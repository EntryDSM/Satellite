package kr.hs.entrydsm.satellite.domain.major.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import java.util.*

@Adapter
class MajorPersistenceAdapter(
    private val majorRepository: MajorRepository,
    private val objectMapper: ObjectMapper
) : MajorPort {

    override suspend fun save(major: Major) =
        majorRepository.save(objectMapper.convertValue<MajorEntity>(major)).awaitFirst()

    override suspend fun queryById(majorId: UUID) =
        majorRepository.findById(majorId).awaitFirstOrNull()

    override suspend fun deleteById(majorId: UUID) {
        majorRepository.deleteById(majorId).awaitFirstOrNull()
    }

    override suspend fun queryByNameContaining(majorName: String): List<Major> =
        majorRepository.findByNameContaining(majorName).collectList().awaitFirst()
}