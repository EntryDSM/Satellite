package kr.hs.entrydsm.exit.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.exit.domain.auth.Authority
import kr.hs.entrydsm.exit.global.config.FilterConfig
import kr.hs.entrydsm.exit.global.security.jwt.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
internal class SecurityConfig(
    private val jwtParser: JwtParser,
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
            .antMatchers(HttpMethod.POST, "/student").permitAll()

            // COMPANY
            .antMatchers(HttpMethod.POST, "/company/sing-up").permitAll()
            .antMatchers(HttpMethod.POST, "/company/standby/{standby-company-id}").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/company/standby/{standby-company-id}").hasAuthority(TEACHER)

            // DOCUMENT
            .antMatchers(HttpMethod.POST, "/document").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/writer-info").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/introduce").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/skill-set").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/project").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/award").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PUT, "/document/certificate").hasAuthority(STUDENT)

            // MAJOR
            .antMatchers(HttpMethod.GET, "/major").hasAnyAuthority(STUDENT, TEACHER, COMPANY)
            .antMatchers(HttpMethod.POST, "/major").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/major").hasAuthority(TEACHER)

            .anyRequest().permitAll()

        http
            .apply(FilterConfig(jwtParser, objectMapper))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}