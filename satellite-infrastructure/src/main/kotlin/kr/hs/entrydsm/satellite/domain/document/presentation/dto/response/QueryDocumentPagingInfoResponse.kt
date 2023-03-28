package kr.hs.entrydsm.satellite.domain.document.presentation.dto.response

import java.util.UUID

data class QueryDocumentPagingInfoResponse(
    val hasNext: Boolean,
    val nextDocumentId: UUID?,
    val hasPrevious: Boolean,
    val previousDocumentId: UUID?,
)