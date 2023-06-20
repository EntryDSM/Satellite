package kr.hs.entrydsm.satellite.domain.library.domain

import java.time.LocalDateTime
import java.util.*

interface LibraryDocument {
    val id: UUID
    val year: Int
    val grade: Int
    val filePath: String
    var accessRight: AccessRight
    val createdAt: LocalDateTime
    val index: List<DocumentIndex>

    val generation: Int
        get() = year - grade - 2013

    fun changeAccessRight(accessRight: AccessRight) {
        this.accessRight = accessRight
    }
}