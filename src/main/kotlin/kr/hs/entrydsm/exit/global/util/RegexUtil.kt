package kr.hs.entrydsm.exit.global.util

object RegexUtil {

    const val PASSWORD_EXP = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+\$).{8,30}"
    const val NUMBER_EXP = "^[0-9]+$"

}