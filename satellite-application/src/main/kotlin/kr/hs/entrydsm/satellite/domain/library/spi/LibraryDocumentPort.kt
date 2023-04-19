package kr.hs.entrydsm.satellite.domain.library.spi

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import java.util.*

interface LibraryDocumentPort {
    suspend fun queryById(libraryDocumentId: UUID): LibraryDocument?
    suspend fun queryAll(): List<LibraryDocument>
    suspend fun queryByYear(year: Int): List<LibraryDocument>
    suspend fun queryByAccessRightNotAndYear(accessRight: AccessRight, year: Int?): List<LibraryDocument>
    suspend fun save(libraryDocument: LibraryDocument): LibraryDocument
}