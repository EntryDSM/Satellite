package kr.hs.entrydsm.repo.domain.library.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import kr.hs.entrydsm.repo.domain.library.persistence.LibraryDocument
import org.springframework.data.repository.CrudRepository

interface LibraryDocumentRepository : CrudRepository<LibraryDocument, UUID> {

    fun findByAccessRightNot(accessRight: AccessRight): List<LibraryDocument>
    fun findByAccessRight(accessRight: AccessRight): List<LibraryDocument>
}