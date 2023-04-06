package kr.hs.entrydsm.satellite.domain.student.persistence

import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.persistence.repository.StudentRepository
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@Adapter
class StudentPersistenceAdapter(
    private val studentRepository: StudentRepository
) : StudentPort {

    override fun save(student: Student): StudentJpaEntity =
        studentRepository.save(StudentJpaEntity.of(student))

    override fun queryById(studentId: UUID) =
        studentRepository.findByIdOrNull(studentId)

    override fun queryByEmail(email: String) =
        studentRepository.findByEmail(email)

    override fun existsByEmail(email: String) =
        studentRepository.existsByEmail(email)
}