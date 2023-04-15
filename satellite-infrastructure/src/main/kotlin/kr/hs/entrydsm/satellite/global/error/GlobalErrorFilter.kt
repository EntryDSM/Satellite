package kr.hs.entrydsm.satellite.global.error

import io.sentry.Sentry
import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.common.error.CustomException
import kr.hs.entrydsm.satellite.global.error.response.DefaultErrorResponse
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
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
            is CustomException -> getErrorMessage(e.errorProperty)
            else -> {
                if (e.cause is CustomException) getErrorMessage((e.cause as CustomException).errorProperty)
                else {
                    Sentry.captureException(e)
                    getErrorMessage(GlobalErrorCode.INTERNAL_SERVER_ERROR)
                }
            }
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