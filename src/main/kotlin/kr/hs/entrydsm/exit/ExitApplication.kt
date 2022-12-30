package kr.hs.entrydsm.exit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
class ExitApplication

fun main(args: Array<String>) {
    runApplication<ExitApplication>(*args)
}
