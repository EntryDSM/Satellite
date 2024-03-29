package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.major.domain.MajorDomain
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort

@UseCase
class CreateMajorUseCase(
    private val majorPort: MajorPort
) {
    suspend fun execute(majorName: String) {
        majorPort.save(
            MajorDomain(name = majorName)
        )
    }
}