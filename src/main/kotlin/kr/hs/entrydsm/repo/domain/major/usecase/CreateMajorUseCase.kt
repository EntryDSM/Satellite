package kr.hs.entrydsm.repo.domain.major.usecase

import kr.hs.entrydsm.repo.domain.major.persistence.Major
import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.major.presentation.dto.request.CreateMajorRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CreateMajorUseCase(
    private val majorRepository: MajorRepository
) {
    @Transactional
    fun execute(request: CreateMajorRequest) {

        majorRepository.save(
            Major(
                name = request.majorName
            )
        )
    }
}