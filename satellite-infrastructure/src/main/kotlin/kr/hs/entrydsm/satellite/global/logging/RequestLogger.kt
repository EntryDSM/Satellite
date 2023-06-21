package kr.hs.entrydsm.satellite.global.logging

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Component
class RequestLogger(
    private val logWriter: LogWriter,
    private val objectMapper: ObjectMapper
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {

        if (exchange.request.headers["Content-Type"]?.contains("multipart/form-data") == true) {
            return@mono chain.filter(exchange).awaitSingleOrNull()
        } else {
            logRequest(exchange.request, exchange.response)
        }

        return@mono chain.filter(exchange).awaitSingleOrNull()
    }

    @Throws(IOException::class)
    private suspend fun logRequest(request: ServerHttpRequest, response: ServerHttpResponse) {

        val requestTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        val requestIP = request.getHeader("X-Forwarded-For")?:"127.0.0.1"
        val method = request.method?.name
        val url = request.uri.toURL().path
        val params = request.queryParams.toString()
        val statusCode = response.statusCode?.value()
        val body = request.securedBody()

        // 2021-05-18 15:51:24.102 :: 127.0.0.1 [POST] (/user/auth?asd=asd 200) {asd: asd}
        val log = String.format("$requestTime :: $requestIP [$method] ($url$params $statusCode) $body")
        logWriter.writeLog(log)
    }

    private suspend fun ServerHttpRequest.securedBody(): String {
        val bodyMap = objectMapper.readValue(
            String(body.awaitSingle().asByteBuffer().array()), MutableMap::class.java
        ) as MutableMap<String, Any>

        bodyMap["password"]?.let {
            bodyMap["password"] = "***"
        }

        return objectMapper.writeValueAsString(bodyMap)
    }

    private fun ServerHttpRequest.getHeader(key: String) = headers[key]?.get(0)
}


