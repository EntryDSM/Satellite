package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.util.*

interface LibraryDocumentRepository : ReactiveMongoRepository<LibraryDocumentEntity, UUID> {
    fun findByYear(year: Int): Flux<LibraryDocumentEntity>
    fun findByAccessRightNot(accessRight: AccessRight): Flux<LibraryDocumentEntity>
    fun findByAccessRightNotAndYear(accessRight: AccessRight, year: Int?): Flux<LibraryDocumentEntity>
}