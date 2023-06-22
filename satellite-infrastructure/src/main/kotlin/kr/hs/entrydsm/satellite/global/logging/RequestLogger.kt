package kr.hs.entrydsm.satellite.global.logging

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.buffer.ByteBufAllocator
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.NettyDataBufferFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
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

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        if (exchange.request.headers["Content-Type"]?.contains("multipart/form-data") == true) {
            chain.filter(exchange)
        } else {
            logRequest(exchange, chain)
        }

    @Throws(IOException::class)
    private fun logRequest(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        val (request, response) = exchange.request to exchange.response
        return DataBufferUtils.join(request.body)
            .flatMap { dataBuffer ->
                val bodyBytes = ByteArray(dataBuffer.readableByteCount()).apply { dataBuffer.read(this) }
                DataBufferUtils.release(dataBuffer)

                logWriter.writeLog(
                    getLogString(
                        request = request,
                        response = response,
                        bodyBytes = bodyBytes
                    )
                )

                return@flatMap chain.filter(
                    exchange.mutate()
                        .request(getMutatedRequest(request, bodyBytes))
                        .build()
                )
            }
    }

    private fun getMutatedRequest(
        request: ServerHttpRequest,
        bodyBytes: ByteArray
    ) = object : ServerHttpRequestDecorator(request) {
        override fun getBody(): Flux<DataBuffer> {
            return DataBufferUtils.read(
                ByteArrayResource(bodyBytes),
                NettyDataBufferFactory(ByteBufAllocator.DEFAULT),
                bodyBytes.size
            )
        }
    }

    private fun getLogString(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        bodyBytes: ByteArray
    ): String {
        val requestTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        val requestIP = request.getHeader("X-Forwarded-For") ?: "127.0.0.1"
        val statusCode = response.statusCode?.value()

        // 2023-06-22 15:51:24.102 :: 127.0.0.1 [POST] 200 /example/auth?asd=asd {key: value}
        return "$requestTime :: $requestIP [${request.method}]" +
                "$statusCode ${request.path}${request.uri.rawQuery?.let { "?$it" } ?: ""} ${securedBody(bodyBytes)}"
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


