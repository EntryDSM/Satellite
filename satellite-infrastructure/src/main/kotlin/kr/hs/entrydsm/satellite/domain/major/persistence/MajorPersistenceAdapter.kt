package kr.hs.entrydsm.satellite.domain.major.persistence

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import java.util.*

private typealias E = MajorEntity

@Adapter
class MajorPersistenceAdapter(
    private val majorRepository: MajorRepository
) : MajorPort {

    override suspend fun save(major: Major) =
        majorRepository.save(MajorEntity.of(major)).awaitSingle()

    override suspend fun queryById(majorId: UUID) =
        majorRepository.findById(majorId).awaitSingleOrNull()

    override suspend fun deleteById(majorId: UUID) {
        majorRepository.deleteById(majorId).awaitSingle()
    }

    override suspend fun queryByNameContaining(majorName: String): List<Major> =
        majorRepository.findByNameContaining(majorName).collectList().awaitSingle()
}