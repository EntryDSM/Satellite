package com.example.exit.domain.auth.util

import java.security.SecureRandom

object GenerateRandomCodeUtil {

    fun randomNumber(number: Int): String {
        val random = SecureRandom()
        val codeList: List<Char> = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val authCodeList: MutableList<String> = mutableListOf()

        for (i: Int in 0 until number) {
            authCodeList.add(i, codeList[random.nextInt(10)].toString());
        }

        return authCodeList.toString().replace("[^0-9]".toRegex(), "")
    }
}