package kr.hs.entrydsm.satellite.domain.library.persistence.repository

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.persistence.LibraryDocumentJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface LibraryDocumentRepository : CrudRepository<LibraryDocumentJpaEntity, UUID> {

    fun findByYear(year: Int): List<LibraryDocumentJpaEntity>
    fun findByAccessRightNot(accessRight: AccessRight): List<LibraryDocumentJpaEntity>
    fun findByAccessRight(accessRight: AccessRight): List<LibraryDocumentJpaEntity>
}