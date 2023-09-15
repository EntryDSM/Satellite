package kr.hs.entrydsm.satellite.global.config.datasource

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import io.r2dbc.spi.Option
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.core.DatabaseClient
import java.nio.ByteBuffer
import java.time.Duration
import java.util.*

@Configuration(proxyBeanMethods = false)
class R2dbcConfig(
    private val r2dbcProperties: R2dbcProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {

        val connectionFactory = ConnectionFactoryBuilder.withOptions(
            builder()
                .option(DRIVER, "pool")
                .option(PROTOCOL, r2dbcProperties.protocol)
                .option(DATABASE, r2dbcProperties.database)
                .option(HOST, r2dbcProperties.host)
                .option(PORT, r2dbcProperties.port)
                .option(USER, r2dbcProperties.username)
                .option(PASSWORD, r2dbcProperties.password)
                .option(SSL, false)
                .option(Option.valueOf("initialSize"), 20)
                .option(Option.valueOf("maxSize"), 20)
                .option(Option.valueOf("allowPublicKeyRetrieval"), true)
        ).build()

        val connectionPoolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
            .initialSize(20)
            .minIdle(20)
            .maxSize(20)
            .maxLifeTime(Duration.ofSeconds(1800))
            .maxIdleTime(Duration.ofSeconds(1800))
            .build()

        return ConnectionPool(connectionPoolConfiguration)
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

    override fun getCustomConverters(): List<Any> {
        return listOf(
            UUIDEncoder(), UUIDDecoder()
        )
    }

    @WritingConverter
    class UUIDEncoder : Converter<UUID, ByteArray> {
        override fun convert(source: UUID): ByteArray =
            ByteBuffer.wrap(ByteArray(16))
                .apply {
                    putLong(source.mostSignificantBits)
                    putLong(source.leastSignificantBits)
                }.array()
    }

    @ReadingConverter
    class UUIDDecoder : Converter<ByteArray, UUID> {
        override fun convert(source: ByteArray): UUID =
            ByteBuffer.wrap(source).run {
                UUID(long, long)
            }
    }
}
