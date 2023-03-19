package kr.hs.entrydsm.repo.global.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["kr.hs.entrydsm.repo"])
@Configuration
class FeignClientConfig