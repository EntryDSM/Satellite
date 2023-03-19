package kr.hs.entrydsm.repo.domain.school.persistence

import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "tbl_school_year")
@Entity
class SchoolYear(

    id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val year: Int

): BaseUUIDEntity(id)