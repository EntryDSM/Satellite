package kr.hs.entrydsm.exit.global.security

import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.global.security.jwt.JwtTokenParser
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
internal class SecurityConfig(
    private val jwtTokenParser: JwtTokenParser,
    private val objectMapper: ObjectMapper
) {

    companion object {
        private val TEACHER = Authority.TEACHER.name
        private val STUDENT = Authority.STUDENT.name
        private val COMPANY = Authority.COMPANY.name
    }

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().cors()

        http
            .authorizeRequests()

            // AUTH
            .antMatchers(HttpMethod.POST, "/auth/**").permitAll()

            // STUDENT
            .antMatchers("/student/profile").hasAuthority(STUDENT)

            // COMPANY
            .antMatchers(HttpMethod.POST, "/company/sing-up").permitAll()
            .antMatchers(HttpMethod.POST, "/company/standby/{standby-company-id}").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/company/standby/{standby-company-id}").hasAuthority(TEACHER)

            .anyRequest().permitAll()

        http
            .apply(kr.hs.entrydsm.exit.global.security.FilterConfig(jwtTokenParser, objectMapper))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}