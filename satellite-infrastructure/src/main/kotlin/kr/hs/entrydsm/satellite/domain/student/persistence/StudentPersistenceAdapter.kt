package kr.hs.entrydsm.satellite.domain.student.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import kr.hs.entrydsm.satellite.domain.student.spi.StudentPort
import java.util.*

@Adapter
class StudentPersistenceAdapter(
    private val studentRepository: StudentRepository,
    private val objectMapper: ObjectMapper
) : StudentPort {

    override suspend fun save(student: Student): Student =
        studentRepository.save(objectMapper.convertValue(student)).awaitSingle()

    override suspend fun queryById(studentId: UUID) =
        studentRepository.findById(studentId).awaitSingleOrNull()

    override suspend fun queryByEmail(email: String) =
        studentRepository.findByEmail(email).awaitSingleOrNull()

    override suspend fun existsByEmail(email: String) =
        studentRepository.existsByEmail(email).awaitSingle()
}