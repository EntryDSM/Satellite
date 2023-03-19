package kr.hs.entrydsm.exit.domain.file.presentation

import kr.hs.entrydsm.exit.domain.file.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.exit.domain.file.usecase.UploadImageUseCase
import kr.hs.entrydsm.exit.global.thirdparty.aws.s3.ImageType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/file")
@RestController
class FileController(
    private val uploadImageUseCase: UploadImageUseCase
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image")
    fun uploadImage(
        @RequestParam file: MultipartFile,
        @RequestParam("type") imageType: ImageType
    ): ImageUrlResponse {
        return uploadImageUseCase.execute(file, imageType)
    }
}