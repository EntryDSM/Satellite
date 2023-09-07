package kr.hs.entrydsm.satellite.domain.library.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
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
        libraryDocumentRepository.save(objectMapper.convertValue(libraryDocument)).awaitFirst()

    override suspend fun queryById(libraryDocumentId: UUID) =
        libraryDocumentRepository.findById(libraryDocumentId).awaitFirstOrNull()

    override suspend fun queryAll(): List<LibraryDocument> =
        libraryDocumentRepository.findAll().collectList().awaitFirst()

    override suspend fun queryByYear(year: Int): List<LibraryDocument> =
        libraryDocumentRepository.findByYear(year).collectList().awaitFirst()

    override suspend fun queryByAccessRightNotAndYear(accessRight: AccessRight, year: Int?): List<LibraryDocument> =
        (year?.let {
            libraryDocumentRepository.findByAccessRightNotAndYear(accessRight, year)
        } ?: libraryDocumentRepository.findByAccessRightNot(accessRight)).collectList().awaitFirst()
}