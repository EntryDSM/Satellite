package kr.hs.entrydsm.exit.domain.document.persistence.element

import java.util.*


class ProjectElement (

    val elementId: UUID = UUID.randomUUID(),
    val name: String,
    val representImagePath: String,
    val startDate: Date,
    val endDate: Date,
    val skillSet: List<String>,
    val description: String,
    val url: String?

)
