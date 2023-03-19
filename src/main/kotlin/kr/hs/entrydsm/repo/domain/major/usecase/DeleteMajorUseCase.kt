package kr.hs.entrydsm.repo.domain.major.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.major.exception.MajorNotFoundException
import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.major.presentation.dto.request.DeleteMajorRequest
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