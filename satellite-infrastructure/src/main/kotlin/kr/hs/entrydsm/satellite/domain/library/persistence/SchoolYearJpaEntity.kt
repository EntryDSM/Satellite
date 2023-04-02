package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "tbl_school_year")
@Entity
class SchoolYearJpaEntity(

    override val id: UUID,

    @Column(columnDefinition = "INT", nullable = false)
    override val year: Int

) : SchoolYear(
    id = id,
    year = year
)