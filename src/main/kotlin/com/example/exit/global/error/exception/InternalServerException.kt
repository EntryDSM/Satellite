package com.example.exit.global.error.exception

import com.example.exit.global.error.GlobalErrorCode
import com.example.exit.global.error.custom.CustomException

object InternalServerException: CustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)