package com.example.exit.global.exception.jwt

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.GlobalCustomException

object GlobalPasswordMissMatchedException : GlobalCustomException(
    GlobalErrorCode.PASSWORD_MISS_MATCHED
)