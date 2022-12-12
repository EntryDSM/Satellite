package com.example.exit.domain.common.custom

interface DomainCustomErrorProperty {

    fun status(): Int

    fun message(): String

    fun code(): String

}