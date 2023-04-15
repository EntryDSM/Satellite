package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.teacher.spi.TeacherPort
import java.util.*

private typealias E = TeacherEntity

@Adapter
class TeacherPersistenceAdapter(
    private val teacherRepository: TeacherRepository
) : TeacherPort {

    override suspend fun queryById(teacherId: UUID) =
        teacherRepository.findById(teacherId).awaitSingleOrNull()

    override suspend fun queryByAccountId(accountId: String) =
        teacherRepository.findByAccountId(accountId).awaitSingleOrNull()
}