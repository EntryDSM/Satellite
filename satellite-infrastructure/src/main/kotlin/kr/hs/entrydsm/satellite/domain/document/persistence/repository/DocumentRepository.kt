package kr.hs.entrydsm.satellite.domain.document.persistence.repository

import kr.hs.entrydsm.satellite.domain.document.persistence.DocumentEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface DocumentRepository : ReactiveMongoRepository<DocumentEntity, UUID> {
    fun findByWriterStudentId(studentId: UUID): Mono<DocumentEntity>
    fun findByYearAndWriterGrade(year: Int, studentGrade: Int): Flux<DocumentEntity>
    fun existsByWriterStudentId(studentId: UUID): Mono<Boolean>
}