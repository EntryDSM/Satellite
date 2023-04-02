package kr.hs.entrydsm.satellite.domain.library.spi

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import java.util.*

interface LibraryDocumentPort {
    fun queryById(libraryDocumentId: UUID): LibraryDocument?
    fun queryAll(): List<LibraryDocument>
    fun queryByAccessRightNot(accessRight: AccessRight): List<LibraryDocument>
    fun save(libraryDocument: LibraryDocument): LibraryDocument
}