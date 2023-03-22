package kr.hs.entrydsm.repo.domain.library.persistence.repository

import kr.hs.entrydsm.repo.domain.library.persistence.AccessRight
import kr.hs.entrydsm.repo.domain.library.persistence.LibraryDocument
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface LibraryDocumentRepository : CrudRepository<LibraryDocument, UUID> {

    fun findByAccessRightNot(accessRight: AccessRight): List<LibraryDocument>
    fun findByAccessRight(accessRight: AccessRight): List<LibraryDocument>
}