package kr.hs.entrydsm.satellite.domain.major.persistence

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "tbl_major")
@Entity
class MajorJpaEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    override val id: UUID,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    override val name: String

) : Major(id, name) {
    companion object {
        fun of(major: Major) = major.run {
            MajorJpaEntity(id, name)
        }
    }
}