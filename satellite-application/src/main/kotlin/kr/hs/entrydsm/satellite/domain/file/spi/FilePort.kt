package kr.hs.entrydsm.satellite.domain.file.spi

interface FilePort {
    suspend fun getPdfFileUrl(filePath: String): String
    suspend fun getImageFileUrl(filePath: String): String
    fun getFileBaseUrl(): String
}