package kr.hs.entrydsm.satellite.common.util

object RegexUtil {

    private const val schoolEmail = "@dsm.hs.kr"

    const val EMAIL_EXP = "^[a-zA-Z0-9.]+$schoolEmail$"

    const val PASSWORD_EXP = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+\$).{8,30}"

    const val NUMBER_EXP = "^[0-9]+$"
}