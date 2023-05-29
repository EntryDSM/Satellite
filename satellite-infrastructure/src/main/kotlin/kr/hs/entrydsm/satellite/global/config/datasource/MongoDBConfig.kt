package kr.hs.entrydsm.satellite.global.config.datasource

import com.fasterxml.uuid.impl.UUIDUtil
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.connection.ClusterSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.bson.UuidRepresentation
import org.bson.types.Binary
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.util.*


@Configuration
@EnableReactiveMongoRepositories(basePackages = ["kr.hs.entrydsm.satellite"], reactiveMongoTemplateRef = "blogMongoTemplate")
class MongoDBConfig(
    private val mongoProperties: MongoProperties
) {

    @Bean
    fun blogMongoTemplate(mongoClient: MongoClient): ReactiveMongoTemplate {
        val factory = SimpleReactiveMongoDatabaseFactory(mongoClient, mongoProperties.database)
        return ReactiveMongoTemplate(factory).apply {
            (converter as MappingMongoConverter).apply {
                customConversions = customConversions()
                afterPropertiesSet()
            }
        }
    }

    @Bean
    fun customConversions(): MongoCustomConversions {
        val converters = listOf(
            BinaryUUIDConverter(),
            UUIDBinaryConverter()
        )
        return MongoCustomConversions(converters)
    }

    @ReadingConverter
    class BinaryUUIDConverter : Converter<Binary, UUID> {
        override fun convert(binary: Binary) =
            UUIDUtil.uuid(binary.data)
    }

    @WritingConverter
    class UUIDBinaryConverter : Converter<UUID, Binary> {
        override fun convert(uuid: UUID) =
            Binary(UUIDUtil.asByteArray(uuid))
    }

    @Bean
    fun mongoClient(): MongoClient {
        val seeds = listOf(ServerAddress(mongoProperties.host))

        val credential =
            MongoCredential.createCredential(
                mongoProperties.username,
                mongoProperties.database,
                mongoProperties.password
            )

        return MongoClients.create(
            MongoClientSettings
                .builder()
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .applyToClusterSettings { builder: ClusterSettings.Builder -> builder.hosts(seeds) }
                .credential(credential)
                .build()
        )
    }
}
