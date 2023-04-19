package kr.hs.entrydsm.satellite.global.security.token

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants
import org.springframework.core.annotation.Order
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Order(-100)
@Component
class JwtFilter(
    private val jwtParser: JwtParser
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {

        resolvedToken(exchange.request)?.let { token ->
            val auth = jwtParser.getAuthentication(token)
            return@mono chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)).awaitSingleOrNull()
        }
        return@mono chain.filter(exchange).awaitSingleOrNull()
    }

    private fun resolvedToken(request: ServerHttpRequest): String? =
        request.headers[JwtConstants.HEADER]?.get(0)?.let {
            if (it.startsWith(JwtConstants.PREFIX)) {
                it.substring(JwtConstants.PREFIX.length + 1)
            } else null
        }
}