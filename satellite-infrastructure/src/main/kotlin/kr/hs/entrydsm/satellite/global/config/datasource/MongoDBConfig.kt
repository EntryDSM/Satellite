package kr.hs.entrydsm.satellite.global.config.datasource

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.connection.ClusterSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.bson.UuidRepresentation
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["kr.hs.entrydsm.satellite"], reactiveMongoTemplateRef = "blogMongoTemplate")
class MongoDBConfig(
    private val mongoProperties: MongoProperties
) {

    @Bean
    fun blogMongoTemplate(mongoClient: MongoClient): ReactiveMongoTemplate {
        val factory = SimpleReactiveMongoDatabaseFactory(mongoClient, mongoProperties.database)
        return ReactiveMongoTemplate(factory)
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
