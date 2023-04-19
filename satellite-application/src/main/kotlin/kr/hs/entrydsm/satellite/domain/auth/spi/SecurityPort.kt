package kr.hs.entrydsm.satellite.domain.auth.spi

import kr.hs.entrydsm.satellite.domain.auth.domain.Authority
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import java.util.*

interface SecurityPort {
    suspend fun getCurrentStudent(): Student
    suspend fun getCurrentUserAuthority(): Authority
    suspend fun getCurrentUserId(): UUID
    fun encyptMatches(rawString: String, encryptedString: String): Boolean
}