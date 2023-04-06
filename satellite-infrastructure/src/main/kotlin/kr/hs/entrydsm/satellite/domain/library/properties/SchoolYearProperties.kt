package kr.hs.entrydsm.satellite.domain.library.properties

import java.util.UUID
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "domain.school.year")
class SchoolYearProperties(
    val id: UUID,
    val secret: String
)