package kr.hs.entrydsm.repo.domain.library.persistence

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table
import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity

@Table(name = "tbl_library")
@Entity
class LibraryDocument(

    id: UUID? = null,

    @Column(columnDefinition = "INT", nullable = false)
    val year: Int,

    @Column(columnDefinition = "INT", nullable = false)
    val grade: Int,

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    val fileUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    val accessRight: AccessRight,

    @Column(columnDefinition = "DATE(6)", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

) : BaseUUIDEntity(id) {

    fun updateLibraryDocumentRight(accessRight: AccessRight) = copy(accessRight = accessRight)

    val generation: Int
        get() = year - grade - 2013

    private fun copy(
        id: UUID = this.id,
        year: Int = this.year,
        grade: Int = this.grade,
        fileUrl: String = this.fileUrl,
        accessRight: AccessRight = this.accessRight,
        createdAt: LocalDateTime = this.createdAt
    ): LibraryDocument {
        return LibraryDocument(
            id = id,
            year = year,
            grade = grade,
            fileUrl = fileUrl,
            accessRight = accessRight,
            createdAt = createdAt
        )
    }
}