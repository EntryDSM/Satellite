package kr.hs.entrydsm.satellite.domain.file.usecase

import kr.hs.entrydsm.satellite.domain.file.exception.InvalidImageException
import kr.hs.entrydsm.satellite.domain.file.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.satellite.common.annotation.UseCase
import kr.hs.entrydsm.satellite.common.thirdparty.aws.s3.AwsS3Adapter
import kr.hs.entrydsm.satellite.common.thirdparty.aws.s3.ImageType
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@UseCase
class UploadImageUseCase(
    private val filePort: FilePort
) {

    fun execute(file: File, imageType: ImageType): ImageUrlResponse {

        val filePath = filePort.saveImage(multipartToFile(file), imageType)

        return ImageUrlResponse(filePath)
    }
}