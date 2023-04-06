package kr.hs.entrydsm.satellite.domain.major.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.major.domain.Major
import kr.hs.entrydsm.satellite.domain.major.persistence.repository.MajorRepository
import kr.hs.entrydsm.satellite.domain.major.spi.MajorPort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@Adapter
class MajorPersistenceAdapter(
    private val majorRepository: MajorRepository
) : MajorPort {

    override fun save(major: Major): MajorJpaEntity =
        majorRepository.save(MajorJpaEntity.of(major))

    override fun queryById(majorId: UUID) =
        majorRepository.findByIdOrNull(majorId)

    override fun deleteById(majorId: UUID) {
        majorRepository.deleteById(majorId)
    }

    override fun queryByNameContaining(majorName: String) =
        majorRepository.findByNameContaining(majorName)

}