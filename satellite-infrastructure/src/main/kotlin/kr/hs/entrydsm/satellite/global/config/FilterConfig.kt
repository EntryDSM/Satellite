package kr.hs.entrydsm.satellite.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.satellite.global.error.GlobalErrorFilter
import kr.hs.entrydsm.satellite.global.security.token.JwtFilter
import kr.hs.entrydsm.satellite.global.security.token.JwtParser
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder
            .addFilterBefore(
                JwtFilter(jwtParser),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                GlobalErrorFilter(objectMapper),
                JwtFilter::class.java
            )
    }
}