package kr.hs.entrydsm.repo.domain.file.presentation

import kr.hs.entrydsm.repo.domain.file.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.repo.domain.file.usecase.UploadImageUseCase
import kr.hs.entrydsm.repo.global.thirdparty.aws.s3.ImageType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
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