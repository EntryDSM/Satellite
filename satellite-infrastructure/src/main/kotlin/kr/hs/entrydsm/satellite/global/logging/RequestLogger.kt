package kr.hs.entrydsm.satellite.global.logging

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.core.io.buffer.DataBufferUtils
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
            logRequest(exchange.request,exchange.response)
        }

        return@mono chain.filter(exchange).awaitSingleOrNull()
    }

    @Throws(IOException::class)
    private suspend fun logRequest(
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ) {
        DataBufferUtils.join(request.body)
            .map { dataBuffer ->
                val bytes = ByteArray(dataBuffer.readableByteCount())
                dataBuffer.read(bytes)
                DataBufferUtils.release(dataBuffer)
                bytes
            }.map {
                // 2023-06-22 15:51:24.102 :: 127.0.0.1 [POST] 200 (/example/auth?asd=asd) {key: value}
                val requestTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                val requestIP = request.getHeader("X-Forwarded-For") ?: "127.0.0.1"
                val statusCode = response.statusCode?.value()
                return@map String.format("$requestTime :: $requestIP [${request.method}] $statusCode /${request.path}?${request.uri.rawQuery ?: ""} ${securedBody(it)}")
            }.map { log ->
                logWriter.writeLog(log)
            }.subscribe()
    }


    private fun securedBody(body: ByteArray): String? {
        val bodyMap = (objectMapper.readValue(String(body),MutableMap::class.java) as MutableMap<String,Any>)
        "password".let {
            if (bodyMap[it] != null) {
                bodyMap[it] = "***"
            }
        }
        return objectMapper.writeValueAsString(bodyMap)
    }

    private fun ServerHttpRequest.getHeader(key: String) = headers[key]?.get(0)
}


