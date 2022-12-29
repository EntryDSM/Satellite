package kr.hs.entrydsm.exit.global.security

import kr.hs.entrydsm.exit.global.error.GlobalErrorFilter
import kr.hs.entrydsm.exit.global.security.jwt.JwtTokenFilter
import kr.hs.entrydsm.exit.global.security.jwt.JwtTokenParser
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtTokenParser: JwtTokenParser,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder
            .addFilterBefore(
                JwtTokenFilter(jwtTokenParser),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                kr.hs.entrydsm.exit.global.error.GlobalErrorFilter(objectMapper),
                JwtTokenFilter::class.java
            )
    }
}