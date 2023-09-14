package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.teacher.spi.TeacherPort
import org.springframework.cache.annotation.Cacheable
import java.util.*

@Adapter
open class TeacherPersistenceAdapter(
    private val teacherRepository: TeacherRepository
) : TeacherPort {

    @Cacheable("teacherById")
    override suspend fun queryById(teacherId: UUID) =
        teacherRepository.findById(teacherId).awaitFirstOrNull()

    override suspend fun queryByAccountId(accountId: String) =
        teacherRepository.findByAccountId(accountId).awaitFirstOrNull()
}