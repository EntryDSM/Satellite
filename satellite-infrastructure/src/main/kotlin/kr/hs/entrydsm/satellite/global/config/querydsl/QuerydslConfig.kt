package kr.hs.entrydsm.satellite.global.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig(
    @PersistenceContext
    private val entityManager: EntityManager
) {

    @Bean
    fun JPAQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}