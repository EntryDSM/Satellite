package kr.hs.entrydsm.satellite.domain.document.dto

import java.util.UUID

data class QueryDocumentPagingInfoResponse(
    val hasNext: Boolean,
    val nextDocumentId: UUID?,
    val hasPrevious: Boolean,
    val previousDocumentId: UUID?,
)