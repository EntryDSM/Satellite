package kr.hs.entrydsm.satellite.domain.major.usecase

import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.domain.major.dto.MajorElement
import kr.hs.entrydsm.satellite.domain.major.dto.MajorListResponse
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort

@ReadOnlyUseCase
class QueryMajorListUseCase(
    private val majorPort: MajorPort
) {
    fun execute(name: String): MajorListResponse {

        val tags = majorPort.queryByNameContaining(name)
            .map { MajorElement(it) }
            .toList()

        return MajorListResponse(tags)
    }
}