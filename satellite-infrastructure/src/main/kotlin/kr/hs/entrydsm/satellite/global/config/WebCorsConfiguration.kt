package kr.hs.entrydsm.satellite.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer


@Configuration
@EnableWebFlux
class WebCorsConfiguration : WebFluxConfigurer {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", corsConfiguration())
        }

    private fun corsConfiguration(): CorsConfiguration =
        CorsConfiguration().apply {
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
}