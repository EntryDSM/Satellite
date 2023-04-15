package kr.hs.entrydsm.satellite.domain.file.spi

import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import java.io.File

interface FilePort {
    suspend fun savePdf(file: File): String
    suspend fun saveImage(file: File, imageType: ImageType): String
    suspend fun getPdfFileUrl(filePath: String): String
    suspend fun getImageFileUrl(filePath: String): String
    suspend fun getFileBaseUrl(): String
}