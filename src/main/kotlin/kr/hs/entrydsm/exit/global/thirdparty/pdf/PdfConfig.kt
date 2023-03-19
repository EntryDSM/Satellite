package kr.hs.entrydsm.exit.global.thirdparty.pdf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


@Configuration
class PdfConfig {

    @Bean
    fun templateEngine(): TemplateEngine {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        return TemplateEngine().apply { this.setTemplateResolver(templateResolver) }
    }
}