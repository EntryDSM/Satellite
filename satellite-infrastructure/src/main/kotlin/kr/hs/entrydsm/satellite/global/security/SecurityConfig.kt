package kr.hs.entrydsm.satellite.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.global.security.token.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
internal class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper
) {

    companion object {
        private val TEACHER = Authority.TEACHER.name
        private val STUDENT = Authority.STUDENT.name
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {

        http.csrf().disable()
            .formLogin().disable()
            .cors()

        http.authorizeExchange()

            // AUTH
            .pathMatchers("/auth/**").permitAll()

            // FILE
            .pathMatchers(HttpMethod.POST, "/file/image").permitAll()

            // STUDENT
            .pathMatchers(HttpMethod.POST, "/student").permitAll()
            .pathMatchers(HttpMethod.GET, "/student").hasAuthority(TEACHER)

            // TEACHER
            .antMatchers(HttpMethod.POST, "/teacher/auth").permitAll()

            // DOCUMENT
            .pathMatchers(HttpMethod.PATCH, "/document/writer-info").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/introduce").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/skill-set").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/project").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/award").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/certificate").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.POST, "/document/submit").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.POST, "/document/submit/cancel").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.POST, "/document/share/{document-id}").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.POST, "/document/share/cancel/{document-id}").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.GET, "/document/my").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.GET, "/document/my/detail").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.GET, "/document/student/{student-id}").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.GET, "/document/shared").hasAnyAuthority(STUDENT, TEACHER)
            .pathMatchers(HttpMethod.GET, "/document/{document-id}/paging").hasAnyAuthority(STUDENT, TEACHER)
            .pathMatchers(HttpMethod.GET, "/document/{document-id}").hasAnyAuthority(STUDENT, TEACHER)

            // LIBRARY
            .pathMatchers(HttpMethod.GET, "/library/student").hasAnyAuthority(STUDENT)
            .pathMatchers(HttpMethod.GET, "/library/teacher").hasAnyAuthority(TEACHER)
            .pathMatchers(HttpMethod.GET, "/library/{library-document-id}/index").hasAnyAuthority(STUDENT, TEACHER)
            .pathMatchers(HttpMethod.GET, "/library/public").permitAll()
            .pathMatchers(HttpMethod.PATCH, "/library/{library-document-id}/access-right").hasAnyAuthority(TEACHER)

            // MAJOR
            .antMatchers(HttpMethod.GET, "/major").permitAll()
            .antMatchers(HttpMethod.POST, "/major").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/major/{major-id}").hasAuthority(TEACHER)

            // FEEDBACK
            .pathMatchers(HttpMethod.POST, "/feedback").hasAnyAuthority(TEACHER)
            .pathMatchers(HttpMethod.PATCH, "/feedback").hasAnyAuthority(TEACHER)
            .pathMatchers(HttpMethod.DELETE, "/feedback").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.POST, "/feedback/apply").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.GET, "/feedback/my").hasAuthority(STUDENT)

            .anyExchange().authenticated()
            .and()
            .httpBasic().and()
            .formLogin()

        return http.build()
    }

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: ReactiveUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager =
        UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService).apply {
            setPasswordEncoder(passwordEncoder)
        }

    @Bean
    protected fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}