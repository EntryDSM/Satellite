package kr.hs.entrydsm.satellite.domain.major.presentation.dto.response

import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement

data class MajorListResponse(
    val majorList: List<MajorElement>
)