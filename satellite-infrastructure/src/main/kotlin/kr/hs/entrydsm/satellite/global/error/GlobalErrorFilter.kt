package kr.hs.entrydsm.satellite.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.common.error.CustomException
import kr.hs.entrydsm.satellite.global.error.response.DefaultErrorResponse
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GlobalErrorFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            sendErrorMessage(response, e.errorProperty)
        } catch (e: Exception) {
            when (e.cause) {
                is CustomException -> sendErrorMessage(response, (e.cause as CustomException).errorProperty)
                else -> {
                    e.printStackTrace()
                    sendErrorMessage(response, GlobalErrorCode.INTERNAL_SERVER_ERROR)
                }
            }
        }
    }

    private fun sendErrorMessage(response: HttpServletResponse, errorProperty: CustomErrorProperty) {

        response.status = errorProperty.status()
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        response.writer.write(
            objectMapper.writeValueAsString(
                DefaultErrorResponse(
                    status = errorProperty.status(),
                    message = errorProperty.message(),
                    code = errorProperty.code()
                )
            )
        )
    }
}