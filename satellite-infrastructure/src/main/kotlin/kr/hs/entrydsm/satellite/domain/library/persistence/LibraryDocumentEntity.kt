package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.common.entity.BaseUUIDEntity
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("libraryDocuments")
data class LibraryDocumentEntity(
    @get:JvmName("getIdentifier")
    override var id: UUID,
    override val year: Int,
    override val grade: Int,
    override val filePath: String,
    override var accessRight: AccessRight,
    override val createdAt: LocalDateTime,
    override val index: Map<String, Int>
) : LibraryDocument, BaseUUIDEntity(id)