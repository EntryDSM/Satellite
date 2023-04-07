package kr.hs.entrydsm.satellite.domain.library.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.LibraryDocument
import kr.hs.entrydsm.satellite.domain.library.persistence.QLibraryDocumentJpaEntity.libraryDocumentJpaEntity
import kr.hs.entrydsm.satellite.domain.library.persistence.repository.LibraryDocumentRepository
import kr.hs.entrydsm.satellite.domain.library.spi.LibraryDocumentPort
import kr.hs.entrydsm.satellite.global.config.findBy
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@Adapter
class LibraryDocumentPersistenceAdapter(
    private val libraryDocumentRepository: LibraryDocumentRepository
) : LibraryDocumentPort {

    override fun save(libraryDocument: LibraryDocument): LibraryDocumentJpaEntity =
        libraryDocumentRepository.save(LibraryDocumentJpaEntity.of(libraryDocument))

    override fun queryById(libraryDocumentId: UUID) =
        libraryDocumentRepository.findByIdOrNull(libraryDocumentId)

    override fun queryAll(): List<LibraryDocument> =
        libraryDocumentRepository.findAll().toList()

    override fun queryByYear(year: Int): List<LibraryDocument> =
        libraryDocumentRepository.findByYear(year)

    override fun queryByAccessRightNotAndYear(accessRight: AccessRight, year: Int?): List<LibraryDocument> =
        libraryDocumentRepository.findBy(
            libraryDocumentJpaEntity.accessRight.ne(accessRight),
            year?.let { libraryDocumentJpaEntity.year.eq(year) }
        )
}