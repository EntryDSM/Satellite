package kr.hs.entrydsm.satellite.domain.major.spi

import kr.hs.entrydsm.satellite.domain.major.domain.Major
import java.util.*

interface MajorPort {
    fun queryById(majorId: UUID): Major?
    fun save(major: Major): Major
    fun deleteById(id: UUID)
    fun queryByNameContaining(majorName: String): List<Major>
}