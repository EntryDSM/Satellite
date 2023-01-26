package kr.hs.entrydsm.exit.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["kr.hs.entrydsm.exit"])
class PropertiesScanConfig