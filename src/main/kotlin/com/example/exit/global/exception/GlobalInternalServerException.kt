package com.example.exit.global.exception

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.GlobalCustomException

object GlobalInternalServerException: GlobalCustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)