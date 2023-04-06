package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
import java.time.LocalDateTime
import java.util.*

data class LibraryDocument(
    val id: UUID = UUID.randomUUID(),
    val year: Int,
    val grade: Int,
    val filePath: String,
    val accessRight: AccessRight,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Domain {
    val generation: Int
        get() = year - grade - 2013

    protected constructor(): this(UUID(0,0), 2023, 1, "", AccessRight.PRIVATE)
}