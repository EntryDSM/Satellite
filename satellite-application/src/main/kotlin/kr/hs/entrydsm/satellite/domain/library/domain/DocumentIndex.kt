package kr.hs.entrydsm.satellite.domain.library.domain

import com.fasterxml.jackson.annotation.JsonProperty
import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement
import java.util.*

data class DocumentIndex(
    val name: String,
    val major: String,
    val studentNumber: Int,
    val page: Int
)

data class DocumentIndexResponse (
    val name: String,
    val major: MajorElement,
    val studentNumber: Int,
    val page: Int
) {
    companion object {
        fun of(index: DocumentIndex) = index.run {
            DocumentIndexResponse(
                name = name,
                major = MajorElement(UUID(0,0), major),
                studentNumber = studentNumber,
                page = page
            )
        }
    }
}

data class DocumentIndexRequest(
    val name: String,
    val major: String,
    @field:JsonProperty("studentNumber")
    val studentNumber: Int,
    val page: Int
)