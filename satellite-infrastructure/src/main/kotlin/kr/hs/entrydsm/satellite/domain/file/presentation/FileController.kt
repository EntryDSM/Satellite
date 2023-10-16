package kr.hs.entrydsm.satellite.domain.file.presentation

import kotlinx.coroutines.reactive.awaitFirst
import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.presentation.dto.response.ImagePathResponse
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter
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
import java.util.*

@RequestMapping("/file")
@RestController
class FileController(
    private val s3Adapter: AwsS3Adapter
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
        return s3Adapter.saveImage(monoFilePart, imageType).map {
            ImagePathResponse(
                imagePath = it,
                baseUrl = s3Adapter.getFileBaseUrl()
            )
        }.awaitFirst()
    }
}
