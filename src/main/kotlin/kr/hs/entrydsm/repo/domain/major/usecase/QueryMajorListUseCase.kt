package kr.hs.entrydsm.repo.domain.major.usecase

import kr.hs.entrydsm.repo.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.repo.domain.major.presentation.dto.response.MajorListResponse
import kr.hs.entrydsm.repo.domain.major.presentation.dto.response.MajorVO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMajorListUseCase(
    private val majorRepository: MajorRepository
) {
    @Transactional(readOnly = false)
    fun execute(name: String): MajorListResponse {

        val tags = majorRepository.findByNameContaining(name)
            .map { MajorVO(it) }
            .toList()

        return MajorListResponse(tags)
    }

}