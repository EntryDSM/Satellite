package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.util.*

interface LibraryDocumentRepository : ReactiveMongoRepository<LibraryDocumentEntity, UUID> {

    fun findByYear(
        year: Int,
        sort: Sort = DEFAULT_SORT
    ): Flux<LibraryDocumentEntity>

    fun findByAccessRightNot(
        accessRight: AccessRight,
        sort: Sort = DEFAULT_SORT
    ): Flux<LibraryDocumentEntity>

    fun findByAccessRightNotAndYear(
        accessRight: AccessRight,
        year: Int?,
        sort: Sort = DEFAULT_SORT
    ): Flux<LibraryDocumentEntity>

    companion object {
        private val DEFAULT_SORT: Sort = Sort.by("year", "grade")
    }
}