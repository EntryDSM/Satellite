package kr.hs.entrydsm.satellite.domain.major.spi

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import java.util.*

interface MajorPort {
    suspend fun queryById(majorId: UUID): Major?
    suspend fun save(major: Major): Major
    suspend fun deleteById(majorId: UUID)
    suspend fun queryByNameContaining(majorName: String): List<Major>
}