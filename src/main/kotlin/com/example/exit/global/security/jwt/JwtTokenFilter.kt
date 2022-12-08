package com.example.exit.global.security.jwt


import com.example.exit.global.security.jwt.properties.JwtProperties
import com.example.exit.global.security.jwt.properties.SecurityProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtTokenParser: JwtTokenParser
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = resolvedToken(request)
        SecurityContextHolder.clearContext()

        token?.let{
            SecurityContextHolder.getContext().authentication = jwtTokenParser.getAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }

    private fun resolvedToken(request: HttpServletRequest): String? =
        request.getHeader(JwtProperties.HEADER)?.also {
            if (it.startsWith(JwtProperties.PREFIX)) {
                return it.substring(JwtProperties.PREFIX.length)
            }
        }
}