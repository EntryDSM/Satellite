package kr.hs.entrydsm.exit.domain.school.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Table(name = "tbl_library")
@Entity
class LibraryDocument(

    id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val year: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val grade: Int,

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    val filePath: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    val accessRight: AccessRight,

    @Column(columnDefinition = "DATE(6)", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

): BaseUUIDEntity(id)