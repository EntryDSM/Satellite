package kr.hs.entrydsm.repo.domain.school.persistence.repository

import java.util.UUID
import kr.hs.entrydsm.repo.domain.school.persistence.LibraryDocument
import org.springframework.data.repository.CrudRepository

interface LibraryDocumentRepository : CrudRepository<LibraryDocument, UUID>