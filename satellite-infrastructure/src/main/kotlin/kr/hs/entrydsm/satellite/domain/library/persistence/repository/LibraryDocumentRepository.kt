package kr.hs.entrydsm.satellite.domain.library.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.persistence.LibraryDocumentJpaEntity
import org.springframework.data.repository.CrudRepository

interface LibraryDocumentRepository : CrudRepository<LibraryDocumentJpaEntity, UUID> {

    fun findByAccessRightNot(accessRight: AccessRight): List<LibraryDocumentJpaEntity>
    fun findByAccessRight(accessRight: AccessRight): List<LibraryDocumentJpaEntity>
}