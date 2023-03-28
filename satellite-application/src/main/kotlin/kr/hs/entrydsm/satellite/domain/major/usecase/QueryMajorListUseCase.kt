package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.response.MajorListResponse
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.response.MajorVO

@ReadOnlyUseCase
class QueryMajorListUseCase(
    private val majorRepository: MajorRepository
) {
    fun execute(name: String): MajorListResponse {

        val tags = majorRepository.findByNameContaining(name)
            .map { MajorVO(it) }
            .toList()

        return MajorListResponse(tags)
    }
}