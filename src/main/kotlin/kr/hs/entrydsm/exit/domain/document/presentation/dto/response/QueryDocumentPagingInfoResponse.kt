package kr.hs.entrydsm.exit.domain.document.presentation.dto.response

import java.util.*

data class QueryDocumentPagingInfoResponse(
    val hasNext: Boolean,
    val nextDocumentId: UUID?,
    val hasPrevious: Boolean,
    val previousDocumentId: UUID?,
)