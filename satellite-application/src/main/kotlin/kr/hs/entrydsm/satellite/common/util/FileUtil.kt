package kr.hs.entrydsm.satellite.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FileUtil {

    fun LocalDateTime.toFileDateFormat(): String =
        format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}