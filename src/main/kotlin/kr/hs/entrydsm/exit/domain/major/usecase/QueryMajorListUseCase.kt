package kr.hs.entrydsm.exit.domain.major.usecase

import kr.hs.entrydsm.exit.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.exit.domain.major.presentation.dto.response.MajorElement
import kr.hs.entrydsm.exit.domain.major.presentation.dto.response.MajorListResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMajorListUseCase(
    private val majorRepository: MajorRepository
) {
    @Transactional(readOnly = false)
    fun execute(name: String): MajorListResponse {

        val tags = majorRepository.findByNameContaining(name)
            .map { MajorElement(it) }
            .toList()

        return MajorListResponse(tags)
    }

}