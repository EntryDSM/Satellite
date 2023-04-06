package kr.hs.entrydsm.satellite.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.global.config.FilterConfig
import kr.hs.entrydsm.satellite.global.security.token.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity(debug = true)
@Configuration
internal class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper
) {

    companion object {
        private val TEACHER = Authority.TEACHER.name
        private val STUDENT = Authority.STUDENT.name
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().cors()

        http
            .authorizeRequests()
            // AUTH
            .antMatchers("/auth/**").permitAll()

            // FILE
            .antMatchers(HttpMethod.POST, "/file/image").permitAll()

            // STUDENT
            .antMatchers(HttpMethod.POST, "/student").permitAll()
            .antMatchers(HttpMethod.GET, "/student").hasAuthority(TEACHER)

            // DOCUMENT
            .antMatchers(HttpMethod.POST, "/document").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/writer-info").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/introduce").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/skill-set").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/project").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/award").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.PATCH, "/document/certificate").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.POST, "/document/submit").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.POST, "/document/submit/cancel").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.POST, "/document/share/{document-id}").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.POST, "/document/share/cancel/{document-id}").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.GET, "/document/my").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.GET, "/document/student/{student-id}").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.GET, "/document/shared").hasAnyAuthority(STUDENT, TEACHER)
            .antMatchers(HttpMethod.GET, "/document/{document-id}/paging").hasAnyAuthority(STUDENT, TEACHER)
            .antMatchers(HttpMethod.GET, "/document/{document-id}").hasAnyAuthority(STUDENT, TEACHER)

            // LIBRARY
            .antMatchers(HttpMethod.GET, "/library/student").hasAnyAuthority(STUDENT)
            .antMatchers(HttpMethod.GET, "/library/teacher").hasAnyAuthority(TEACHER)
            .antMatchers(HttpMethod.GET, "/library/public").permitAll()
            .antMatchers(HttpMethod.PATCH, "/library/{library-document-id}/access-right").hasAnyAuthority(STUDENT, TEACHER)

            // MAJOR
            .antMatchers(HttpMethod.GET, "/major").hasAnyAuthority(STUDENT, TEACHER)
            .antMatchers(HttpMethod.POST, "/major").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/major").hasAuthority(TEACHER)

            // FEEDBACK
            .antMatchers(HttpMethod.POST, "/feedback").hasAnyAuthority(TEACHER)
            .antMatchers(HttpMethod.PATCH, "/feedback").hasAnyAuthority(TEACHER)
            .antMatchers(HttpMethod.DELETE, "/feedback").hasAuthority(TEACHER)
            .antMatchers(HttpMethod.POST, "/feedback/apply").hasAuthority(STUDENT)
            .antMatchers(HttpMethod.GET, "/feedback/my").hasAuthority(STUDENT)
            .anyRequest().authenticated()

        http
            .apply(FilterConfig(jwtParser, objectMapper))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}