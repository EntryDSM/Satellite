package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import java.util.*

data class QueryDocumentRequest(

    val name: String? = "",

    val grade: String?,

    val classNum: String?,

    val majorId: UUID?
)