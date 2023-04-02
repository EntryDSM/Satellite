package kr.hs.entrydsm.satellite.domain.file.spi

import java.io.File

interface FilePort {
    fun saveFile(file: File): String
}