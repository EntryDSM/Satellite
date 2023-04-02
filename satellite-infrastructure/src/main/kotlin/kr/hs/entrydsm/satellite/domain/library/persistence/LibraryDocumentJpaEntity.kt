package kr.hs.entrydsm.satellite.domain.library.persistence

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import javax.persistence.Id

@Table(name = "tbl_library")
@Entity
class LibraryDocumentJpaEntity(

    @Id
    override val id: UUID,

    @Column(columnDefinition = "INT", nullable = false)
    override val year: Int,

    @Column(columnDefinition = "INT", nullable = false)
    override val grade: Int,

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    override val fileUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    override val accessRight: AccessRight,

    @Column(columnDefinition = "DATE(6)", nullable = false)
    override val createdAt: LocalDateTime = LocalDateTime.now()

) : LibraryDocument(
    id = id,
    year = year,
    grade = grade,
    fileUrl = fileUrl,
    accessRight = accessRight,
    createdAt = createdAt
)