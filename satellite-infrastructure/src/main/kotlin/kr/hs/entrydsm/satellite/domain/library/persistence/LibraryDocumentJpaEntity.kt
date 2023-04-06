package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "tbl_library_document")
@Entity
class LibraryDocumentJpaEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
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

    @Column(columnDefinition = "DATE", nullable = false)
    override val createdAt: LocalDateTime = LocalDateTime.now()

) : LibraryDocument(
    id = id,
    year = year,
    grade = grade,
    fileUrl = fileUrl,
    accessRight = accessRight,
    createdAt = createdAt
) {
    companion object {
        fun of(libraryDocument: LibraryDocument) = libraryDocument.run {
            LibraryDocumentJpaEntity(
                id = id,
                year = year,
                grade = grade,
                fileUrl = fileUrl,
                accessRight = accessRight,
                createdAt = createdAt
            )
        }
    }
}