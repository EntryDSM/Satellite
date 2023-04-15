package kr.hs.entrydsm.satellite.global.config.datasource

import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration(proxyBeanMethods = false)
class R2dbcConfig(
    private val r2dbcProperties: R2dbcProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactoryBuilder.withOptions(
            builder()
                .option(DRIVER, "mariadb")
                .option(PROTOCOL, r2dbcProperties.protocol)
                .option(DATABASE, r2dbcProperties.database)
                .option(HOST, r2dbcProperties.host)
                .option(PORT, r2dbcProperties.port)
                .option(USER, r2dbcProperties.username)
                .option(PASSWORD, r2dbcProperties.password)
                .option(SSL, false)
        ).build()
    }

    @ConstructorBinding
    @ConfigurationProperties("spring.r2dbc")
    class R2dbcProperties(
        val protocol: String,
        val host: String,
        val port: Int,
        val database: String,
        val username: String,
        val password: String
    )

    @Bean
    fun r2dbcDatabaseClient(connectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.builder().connectionFactory(connectionFactory).build()
}