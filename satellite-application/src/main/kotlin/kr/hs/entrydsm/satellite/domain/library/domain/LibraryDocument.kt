package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.time.LocalDateTime
import java.util.*

class LibraryDocument(
    val id: UUID = UUID.randomUUID(),
    val year: Int,
    val grade: Int,
    val fileUrl: String,
    val accessRight: AccessRight,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Domain {
    val generation: Int
        get() = year - grade - 2013

    fun copy(
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