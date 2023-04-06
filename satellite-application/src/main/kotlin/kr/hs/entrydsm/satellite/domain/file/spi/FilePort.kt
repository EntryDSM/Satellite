package kr.hs.entrydsm.satellite.domain.file.spi

import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import java.io.File

interface FilePort {
    fun savePdf(file: File): String
    fun saveImage(file: File, imageType: ImageType): String
    fun getPdfFileUrl(filePath: String): String
    fun getImageFileUrl(filePath: String): String
}