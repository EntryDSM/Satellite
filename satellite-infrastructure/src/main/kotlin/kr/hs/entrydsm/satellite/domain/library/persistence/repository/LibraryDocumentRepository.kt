package kr.hs.entrydsm.satellite.domain.library.persistence.repository

import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentJpaEntity
import kr.hs.entrydsm.satellite.domain.library.persistence.LibraryDocumentJpaEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface LibraryDocumentRepository : CrudRepository<LibraryDocumentJpaEntity, UUID>, QuerydslPredicateExecutor<LibraryDocumentJpaEntity> {
    fun findByYear(year: Int): List<LibraryDocumentJpaEntity>
}