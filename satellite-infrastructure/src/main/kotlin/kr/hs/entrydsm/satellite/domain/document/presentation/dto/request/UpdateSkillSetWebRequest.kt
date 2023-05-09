package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import javax.validation.constraints.Size

data class UpdateSkillSetWebRequest(

    @field:Size(max = 14)
    val skillList: List<String>
)