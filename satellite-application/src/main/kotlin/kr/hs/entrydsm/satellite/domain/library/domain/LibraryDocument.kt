package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.time.LocalDateTime
import java.util.*

open class LibraryDocument(
    open val id: UUID = UUID.randomUUID(),
    open val year: Int,
    open val grade: Int,
    open val fileUrl: String,
    open val accessRight: AccessRight,
    open val createdAt: LocalDateTime = LocalDateTime.now()
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