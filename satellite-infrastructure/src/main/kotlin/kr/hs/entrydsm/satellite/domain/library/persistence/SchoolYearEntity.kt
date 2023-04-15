package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.SchoolYear
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "tbl_school_year")
class SchoolYearEntity(
    @Id
    override val id: UUID,
    override val year: Int
) : SchoolYear(
    id, year
) {
    companion object {
        fun of(schoolYear: SchoolYear) = schoolYear.run {
            SchoolYearEntity(id, year)
        }
    }
}