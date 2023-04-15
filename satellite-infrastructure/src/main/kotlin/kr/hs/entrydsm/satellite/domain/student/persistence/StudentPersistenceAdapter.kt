package kr.hs.entrydsm.satellite.domain.student.persistence

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.util.*

private typealias E = StudentEntity

@Adapter
class StudentPersistenceAdapter(
    private val studentRepository: StudentRepository
) : StudentPort {

    override suspend fun save(student: Student) =
        studentRepository.save(StudentEntity.of(student)).awaitSingle()

    override suspend fun queryById(studentId: UUID) =
        studentRepository.findById(studentId).awaitSingleOrNull()

    override suspend fun queryByEmail(email: String) =
        studentRepository.findByEmail(email).awaitSingleOrNull()

    override suspend fun existsByEmail(email: String) =
        studentRepository.existsByEmail(email).awaitSingle()
}