package com.example.exit.global.error.custom

abstract class CustomException(
    val errorProperty: CustomErrorProperty
) : RuntimeException()