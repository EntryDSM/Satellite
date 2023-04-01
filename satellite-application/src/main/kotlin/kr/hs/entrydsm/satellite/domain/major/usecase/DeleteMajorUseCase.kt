package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import java.util.*

@UseCase
class DeleteMajorUseCase(
    private val majorPort: MajorPort
) {
    fun execute(majorId: UUID) {
        val major = majorPort.queryById(majorId) ?: throw MajorNotFoundException
        majorPort.deleteById(major.id)
    }
}