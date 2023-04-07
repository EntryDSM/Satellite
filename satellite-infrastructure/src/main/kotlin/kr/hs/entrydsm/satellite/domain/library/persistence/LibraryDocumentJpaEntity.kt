package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("libraryDocuments")
class LibraryDocumentJpaEntity(
    id: UUID,
    year: Int,
    grade: Int,
    filePath: String,
    accessRight: AccessRight,
    createdAt: LocalDateTime,
    index: Map<String, Int>
) : LibraryDocument(
    id, year, grade, filePath, accessRight, createdAt, index
) {
    companion object {
        fun of(libraryDocument: LibraryDocument) = libraryDocument.run {
            LibraryDocumentJpaEntity(
                id, year, grade, filePath, accessRight, createdAt, index
            )
        }
    }
}