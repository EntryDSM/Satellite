package kr.hs.entrydsm.repo.domain.school.persistence.repository

import kr.hs.entrydsm.repo.domain.school.persistence.LibraryDocument
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface LibraryDocumentRepository : CrudRepository<LibraryDocument, UUID>