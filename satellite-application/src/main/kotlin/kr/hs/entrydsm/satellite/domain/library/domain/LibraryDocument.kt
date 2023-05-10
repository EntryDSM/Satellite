package kr.hs.entrydsm.satellite.domain.library.domain

import kr.hs.entrydsm.satellite.global.domain.Domain
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

data class LibraryDocumentDomain(
    override val id: UUID = UUID.randomUUID(),
    override val year: Int,
    override val grade: Int,
    override val filePath: String,
    override var accessRight: AccessRight,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val index: List<DocumentIndex>
) : LibraryDocument, Domain