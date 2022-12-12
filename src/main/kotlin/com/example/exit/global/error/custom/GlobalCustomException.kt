package com.example.exit.global.error.custom

abstract class GlobalCustomException(
    val errorProperty: CustomErrorProperty
) : RuntimeException()