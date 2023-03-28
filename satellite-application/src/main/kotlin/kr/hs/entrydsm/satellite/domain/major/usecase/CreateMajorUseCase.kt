package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.major.persistence.Major
import kr.hs.entrydsm.satellite.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.request.CreateMajorRequest

@UseCase
class CreateMajorUseCase(
    private val majorRepository: MajorRepository
) {
    fun execute(request: CreateMajorRequest) {

        majorRepository.save(
            Major(
                name = request.majorName
            )
        )
    }
}