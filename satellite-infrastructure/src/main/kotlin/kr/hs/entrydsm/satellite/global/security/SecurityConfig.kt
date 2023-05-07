package kr.hs.entrydsm.satellite.global.security

import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
internal class SecurityConfig {

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
            .pathMatchers(HttpMethod.OPTIONS).permitAll()

            // AUTH
            .pathMatchers("/auth/**").permitAll()

            // FILE
            .pathMatchers(HttpMethod.POST, "/file/image").permitAll()

            // STUDENT
            .pathMatchers(HttpMethod.POST, "/student").permitAll()
            .pathMatchers(HttpMethod.GET, "/student").hasAuthority(TEACHER)

            // TEACHER
            .pathMatchers(HttpMethod.POST, "/teacher/auth").permitAll()

            // DOCUMENT
            .pathMatchers(HttpMethod.PATCH, "/document/writer-info").hasAuthority(STUDENT)
            .pathMatchers(HttpMethod.PATCH, "/document/profile-image").hasAuthority(STUDENT)
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
            .pathMatchers(HttpMethod.GET, "/major").permitAll()
            .pathMatchers(HttpMethod.POST, "/major").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.DELETE, "/major/{major-id}").hasAuthority(TEACHER)

            // FEEDBACK
            .pathMatchers(HttpMethod.POST, "/feedback").hasAnyAuthority(TEACHER)
            .pathMatchers(HttpMethod.PATCH, "/feedback").hasAnyAuthority(TEACHER)
            .pathMatchers(HttpMethod.DELETE, "/feedback").hasAuthority(TEACHER)
            .pathMatchers(HttpMethod.PATCH, "/feedback/apply").hasAuthority(STUDENT)
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
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}