package kr.hs.entrydsm.exit.domain.major.presentation.dto.request

import org.jetbrains.annotations.NotNull
import java.util.*

data class DeleteMajorRequest(

    @field:NotNull
    val majorId: UUID

)