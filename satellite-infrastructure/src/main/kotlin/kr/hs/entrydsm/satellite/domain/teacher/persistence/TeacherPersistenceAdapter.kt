package kr.hs.entrydsm.satellite.domain.teacher.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.teacher.domain.Teacher
import kr.hs.entrydsm.satellite.domain.teacher.persistence.repository.TeacherRepository
import kr.hs.entrydsm.satellite.domain.teacher.spi.TeacherPort

@Adapter
class TeacherPersistenceAdapter(
    private val teacherRepository: TeacherRepository
) : TeacherPort {
    override fun queryByAccountId(accountId: String) =
        teacherRepository.findByAccountId(accountId)
}