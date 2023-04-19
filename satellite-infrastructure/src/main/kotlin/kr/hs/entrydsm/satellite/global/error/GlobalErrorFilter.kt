package kr.hs.entrydsm.satellite.global.error

import io.sentry.Sentry
import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.common.error.CustomException
import kr.hs.entrydsm.satellite.global.error.response.BindErrorResponse
import kr.hs.entrydsm.satellite.global.error.response.DefaultErrorResponse
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono

@Order(-2)
@Component
class GlobalErrorFilter(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    configurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext) {

    init {
        this.setMessageWriters(configurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), this::handleError)

    private fun handleError(request: ServerRequest) =
        when (val e = super.getError(request)) {
            is CustomException -> {
                e.printStackTrace()
                getErrorMessage(e.errorProperty)
            }
            is WebExchangeBindException -> getBindErrorMessage(e) // request validation
            is ServerWebInputException -> getErrorMessage(GlobalErrorCode.BAD_REQUEST) // request null
            is MethodNotAllowedException -> getErrorMessage(GlobalErrorCode.METHOD_NOT_ALLOWED)
            else -> {
                e.printStackTrace()
                if (e.cause is CustomException) getErrorMessage((e.cause as CustomException).errorProperty)
                else {
                    Sentry.captureException(e)
                    getErrorMessage(GlobalErrorCode.INTERNAL_SERVER_ERROR)
                }
            }
        }

    private fun getBindErrorMessage(e: BindingResult): Mono<ServerResponse> {
        val errorMap = HashMap<String,String?>()
            .apply { e.fieldErrors.forEach { put(it.field,it.defaultMessage) } }

        return ServerResponse
            .status(HttpStatus.BAD_REQUEST)
            .bodyValue(
                BindErrorResponse(
                    status = GlobalErrorCode.BAD_REQUEST.status(),
                    fieldError = errorMap
                )
            )
    }

    private fun getErrorMessage(errorProperty: CustomErrorProperty): Mono<ServerResponse> =
        ServerResponse
            .status(errorProperty.status())
            .bodyValue(
                DefaultErrorResponse(
                    status = errorProperty.status(),
                    message = errorProperty.message(),
                    code = errorProperty.code()
                )
            )
}