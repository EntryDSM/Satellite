package kr.hs.entrydsm.exit.global.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["kr.hs.entrydsm.exit"])
@Configuration
class FeignClientConfig