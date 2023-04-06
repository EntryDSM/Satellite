package kr.hs.entrydsm.satellite.global.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["kr.hs.entrydsm.satellite"])
@Configuration
class FeignClientConfig