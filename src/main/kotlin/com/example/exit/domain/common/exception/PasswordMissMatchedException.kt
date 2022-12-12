package com.example.exit.domain.common.exception

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.GlobalCustomException

object PasswordMissMatchedException : GlobalCustomException(
    GlobalErrorCode.PASSWORD_MISS_MATCHED
)