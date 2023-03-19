package kr.hs.entrydsm.exit.domain.file.presentation

import kr.hs.entrydsm.exit.domain.file.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.exit.domain.file.usecase.GeneratePdfUseCase
import kr.hs.entrydsm.exit.domain.file.usecase.UploadImageUseCase
import kr.hs.entrydsm.exit.global.thirdparty.aws.s3.ImageType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

@RequestMapping("/file")
@RestController
class FileController(
    private val uploadImageUseCase: UploadImageUseCase,
    private val generatePdfUseCase: GeneratePdfUseCase
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image")
    fun uploadImage(
        @RequestParam file: MultipartFile,
        @RequestParam("type") imageType: ImageType
    ): ImageUrlResponse {
        return uploadImageUseCase.execute(file, imageType)
    }

    @GetMapping
    fun exportAllPointHistory(httpResponse: HttpServletResponse): ByteArray {
        val response = generatePdfUseCase.execute()
        httpResponse.setHeader(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=${URLEncoder.encode("qweqwe", "UTF-8")}.pdf"
        )
        return response
    }
}