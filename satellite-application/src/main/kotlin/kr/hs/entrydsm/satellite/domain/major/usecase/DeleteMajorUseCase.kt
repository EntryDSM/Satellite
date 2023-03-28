package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.satellite.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.request.DeleteMajorRequest
import org.springframework.data.repository.findByIdOrNull

@UseCase
class DeleteMajorUseCase(
    private val majorRepository: MajorRepository
) {
    fun execute(request: DeleteMajorRequest) {

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        majorRepository.deleteById(major.id)
    }
}