package kr.hs.entrydsm.exit.domain.school.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.util.UUID

@ConstructorBinding
@ConfigurationProperties(prefix = "domain.school.year")
class SchoolYearProperties(
    val id: UUID,
    val secret: String
)