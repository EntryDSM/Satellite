package kr.hs.entrydsm.satellite.domain.student.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
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
        studentRepository.save(objectMapper.convertValue(student)).awaitFirst()

    override suspend fun queryById(studentId: UUID) =
        studentRepository.findById(studentId).awaitFirstOrNull()

    override suspend fun queryByEmail(email: String) =
        studentRepository.findByEmail(email).awaitFirstOrNull()

    override suspend fun existsByEmail(email: String): Boolean =
        studentRepository.existsByEmail(email).awaitFirst()
}