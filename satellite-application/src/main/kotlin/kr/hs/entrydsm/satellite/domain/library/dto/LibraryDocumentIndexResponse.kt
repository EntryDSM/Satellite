package kr.hs.entrydsm.satellite.domain.library.dto

import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndex

data class LibraryDocumentIndexResponse(
    val index: List<DocumentIndex>
)