package kr.hs.entrydsm.exit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
class ExitApplication

fun main(args: Array<String>) {
    runApplication<ExitApplication>(*args)
}
