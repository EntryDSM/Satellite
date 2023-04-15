package kr.hs.entrydsm.satellite.domain.major.persistence

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_major")
class MajorEntity(
    @Id
    override val id: UUID,
    override val name: String
) : Major(id, name) {
    companion object {
        fun of(major: Major) = major.run {
            MajorEntity(id, name)
        }
    }
}