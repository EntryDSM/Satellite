package kr.hs.entrydsm.satellite.domain.student.spi

import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

interface StudentPort {
    suspend fun save(student: Student): Student
    suspend fun queryById(studentId: UUID): Student?
    suspend fun queryByEmail(email: String): Student?
    suspend fun existsByEmail(email: String): Boolean
}