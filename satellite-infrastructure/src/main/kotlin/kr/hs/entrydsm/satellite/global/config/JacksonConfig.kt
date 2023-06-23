package kr.hs.entrydsm.satellite.global.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Configuration
class JacksonConfig {
    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
            .serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .serializers(LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm")))
            .serializers(LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    }

    @Bean
    fun localDateFormatter(): Formatter<LocalDateTime> {
        return object : Formatter<LocalDateTime> {
            override fun parse(text: String, locale: Locale) = LocalDateTime.parse(text)
            override fun print(localDateTime: LocalDateTime,locale: Locale) = DateTimeFormatter.ISO_DATE.format(localDateTime)
        }
    }
}