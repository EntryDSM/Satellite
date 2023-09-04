package kr.hs.entrydsm.satellite.global.exception

import kr.hs.entrydsm.satellite.common.error.CustomErrorProperty
import kr.hs.entrydsm.satellite.global.error.GlobalCustomException
import kr.hs.entrydsm.satellite.global.error.GlobalErrorCode

object ForbiddenException : GlobalCustomException(
    GlobalErrorCode.FORBIDDEN
)

class DynamicForbiddenException(message: String?) : GlobalCustomException(
    object : CustomErrorProperty {
        override fun status() = GlobalErrorCode.FORBIDDEN.status()
        override fun message() = message ?: ""
        override fun code() = GlobalErrorCode.FORBIDDEN.code()
    }
)

object InternalServerError : GlobalCustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)