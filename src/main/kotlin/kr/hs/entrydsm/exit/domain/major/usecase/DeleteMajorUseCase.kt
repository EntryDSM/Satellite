package kr.hs.entrydsm.exit.domain.major.usecase

import kr.hs.entrydsm.exit.domain.document.exception.MajorNotFoundException
import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.exit.domain.major.presentation.dto.request.DeleteMajorRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class DeleteMajorUseCase(
    private val majorRepository: MajorRepository
) {
    @Transactional
    fun execute(request: DeleteMajorRequest) {

        val major = majorRepository.findByIdOrNull(request.majorId) ?: throw MajorNotFoundException

        majorRepository.deleteById(major.id)
    }
}