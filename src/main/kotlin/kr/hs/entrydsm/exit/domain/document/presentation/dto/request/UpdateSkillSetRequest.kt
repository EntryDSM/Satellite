package kr.hs.entrydsm.exit.domain.document.presentation.dto.request

import org.jetbrains.annotations.NotNull
import java.util.*

data class UpdateSkillSetRequest (

    @field:NotNull
    val documentId: UUID,

    @field:NotNull
    val skillList: List<String>
)