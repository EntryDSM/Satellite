package kr.hs.entrydsm.repo.domain.school.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity

@Table(name = "tbl_school_year")
@Entity
class SchoolYear(

    id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val year: Int

) : BaseUUIDEntity(id)