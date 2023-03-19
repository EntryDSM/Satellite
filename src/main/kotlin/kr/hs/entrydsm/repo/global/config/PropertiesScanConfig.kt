package kr.hs.entrydsm.repo.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["kr.hs.entrydsm.repo"])
class PropertiesScanConfig