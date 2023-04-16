package kr.hs.entrydsm.satellite.domain.file.presentation

import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.presentation.dto.response.ImagePathResponse
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.global.exception.InvalidFileException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@RequestMapping("/file")
@RestController
class FileController(
    private val filePort: FilePort
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image")
    fun uploadImage(
        @RequestParam file: MultipartFile,
        @RequestParam("type") imageType: ImageType
    ): ImagePathResponse {
        return ImagePathResponse(
            imagePath = filePort.saveImage(multipartToFile(file), imageType),
            baseUrl = filePort.getFileBaseUrl()
        )
    }

    private fun multipartToFile(multipartFile: MultipartFile?): File {
        if (multipartFile == null || multipartFile.isEmpty || multipartFile.originalFilename == null) {
            throw InvalidFileException
        }
        try {
            return File("${UUID.randomUUID()}_${multipartFile.originalFilename}")
                .also { FileOutputStream(it).use { outputStream -> outputStream.write(multipartFile.bytes) } }
        } catch (e: IOException) {
            throw InvalidFileException
        }
    }
}