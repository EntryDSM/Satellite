package kr.hs.entrydsm.satellite.domain.major.presentation.dto.request

import java.util.UUID
import javax.validation.constraints.NotNull

data class DeleteMajorRequest(
    @field:NotNull
    val majorId: UUID
)