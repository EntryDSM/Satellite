package kr.hs.entrydsm.satellite.domain.library.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import java.util.*

@Adapter
class LibraryDocumentPersistenceAdapter(
    private val libraryDocumentRepository: LibraryDocumentRepository,
    private val objectMapper: ObjectMapper
) : LibraryDocumentPort {

    override suspend fun save(libraryDocument: LibraryDocument): LibraryDocumentEntity =
        libraryDocumentRepository.save(objectMapper.convertValue(libraryDocument)).awaitSingle()

    override suspend fun queryById(libraryDocumentId: UUID) =
        libraryDocumentRepository.findById(libraryDocumentId).awaitSingleOrNull()

    override suspend fun queryAll(): List<LibraryDocument> =
        libraryDocumentRepository.findAll().collectList().awaitSingle()

    override suspend fun queryByYear(year: Int): List<LibraryDocument> =
        libraryDocumentRepository.findByYear(year).collectList().awaitSingle()

    override suspend fun queryByAccessRightNotAndYear(accessRight: AccessRight, year: Int?): List<LibraryDocument> =
        (year?.let {
            libraryDocumentRepository.findByAccessRightNotAndYear(accessRight, year)
        } ?: libraryDocumentRepository.findByAccessRightNot(accessRight)).collectList().awaitSingle()
}