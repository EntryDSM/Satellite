package kr.hs.entrydsm.repo.domain.document.presentation.dto.request

import java.util.UUID

data class QueryDocumentRequest(

    val name: String? = "",

    val grade: String?,

    val classNum: String?,

    val majorId: UUID?
)