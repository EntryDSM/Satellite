package kr.hs.entrydsm.satellite.global.security.token

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import kr.hs.entrydsm.satellite.global.security.token.properties.JwtConstants
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtFilter(
    private val jwtParser: JwtParser
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {
        val token = resolvedToken(exchange.request)
        SecurityContextHolder.clearContext()
        token?.let {
            SecurityContextHolder.getContext().authentication = jwtParser.getAuthentication(token)
        }
        return@mono chain.filter(exchange).awaitSingle()
    }

    private fun resolvedToken(request: ServerHttpRequest): String? =
        request.headers[JwtConstants.HEADER]?.run {
            val headerData = get(0)
            if (headerData.startsWith(JwtConstants.PREFIX)) {
                headerData.substring(JwtConstants.PREFIX.length)
            } else null
        }
}