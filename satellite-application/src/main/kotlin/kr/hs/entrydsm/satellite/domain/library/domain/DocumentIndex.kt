package kr.hs.entrydsm.satellite.domain.library.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class DocumentIndex(
    val name: String,
    val major: String,
    val studentNumber: Int,
    val page: Int
)

data class DocumentIndexRequest(
    val name: String,
    val major: String,
    @field:JsonProperty("studentNumber")
    val studentNumber: Int,
    val page: Int
)