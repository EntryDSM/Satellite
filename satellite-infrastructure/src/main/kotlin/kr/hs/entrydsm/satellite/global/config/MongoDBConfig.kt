package kr.hs.entrydsm.satellite.global.config

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.connection.ClusterSettings
import org.bson.UuidRepresentation
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["kr.hs.entrydsm.satellite"], mongoTemplateRef = "blogMongoTemplate")
class MongoDBConfig(
    private val mongoProperties: MongoProperties,
) {

    @Bean
    fun blogMongoTemplate(mongoClient: MongoClient): MongoTemplate {
        val factory: MongoDatabaseFactory = SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.database)
        return MongoTemplate(factory)
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
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .applyToClusterSettings { builder: ClusterSettings.Builder -> builder.hosts(seeds) }
                .credential(credential)
                .build()
        )
    }
}