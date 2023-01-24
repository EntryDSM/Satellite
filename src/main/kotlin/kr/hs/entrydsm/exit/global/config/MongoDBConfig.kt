package kr.hs.entrydsm.exit.global.config

import com.mongodb.client.MongoClient
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["kr.hs.entrydsm.exit"], mongoTemplateRef = "blogMongoTemplate")
class MongoDBConfig(
    private val mongoProperties: MongoProperties
) {

    @Bean
    fun blogMongoTemplate(mongoClient: MongoClient): MongoTemplate {
        val factory: MongoDatabaseFactory = SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.database)
        return MongoTemplate(factory)
    }

}