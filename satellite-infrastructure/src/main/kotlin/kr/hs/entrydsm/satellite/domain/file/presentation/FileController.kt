package kr.hs.entrydsm.satellite.domain.file.presentation

import kotlinx.coroutines.reactive.awaitFirst
import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.presentation.dto.response.ImagePathResponse
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.io.File
import java.io.FileOutputStream
import java.util.*

@RequestMapping("/file")
@RestController
class FileController(
    private val filePort: FilePort
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image", consumes = [
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.IMAGE_PNG_VALUE,
        MediaType.IMAGE_JPEG_VALUE
    ])
    suspend fun uploadImage(
        @RequestPart("file") monoFilePart: Mono<FilePart>,
        @RequestParam("type") imageType: ImageType
    ): ImagePathResponse {

        val filePart = monoFilePart.awaitFirst()

        return ImagePathResponse(
            imagePath = filePort.saveImage(filePart.toFile(), imageType),
            baseUrl = filePort.getFileBaseUrl()
        )
    }

    private suspend fun FilePart.toFile() = File(filename())
            .also { FileOutputStream(it).use { outputStream ->
                DataBufferUtils.write(content(), outputStream).awaitFirst()
            } }
}
