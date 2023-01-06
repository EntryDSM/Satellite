package kr.hs.entrydsm.exit.domain.image.presentation

import kr.hs.entrydsm.exit.domain.image.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.exit.domain.image.usecase.UploadImageUseCase
import kr.hs.entrydsm.exit.global.aws.s3.ImageType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/image")
@RestController
class ImageController(
    private val uploadImageUseCase: UploadImageUseCase
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun uploadImage(
        @RequestParam file: MultipartFile,
        @RequestParam("type") imageType: ImageType
    ): ImageUrlResponse {
        return uploadImageUseCase.execute(file, imageType)
    }

}