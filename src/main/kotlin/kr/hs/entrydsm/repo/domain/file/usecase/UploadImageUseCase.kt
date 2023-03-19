package kr.hs.entrydsm.repo.domain.file.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.UseCase
import kr.hs.entrydsm.repo.domain.file.exception.InvalidImageException
import kr.hs.entrydsm.repo.domain.file.presentation.dto.response.ImageUrlResponse
import kr.hs.entrydsm.repo.global.thirdparty.aws.s3.AwsS3Adapter
import kr.hs.entrydsm.repo.global.thirdparty.aws.s3.ImageType
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@UseCase
class UploadImageUseCase(
    private val awsS3Adapter: AwsS3Adapter
) {

    fun execute(file: MultipartFile, imageType: ImageType): ImageUrlResponse {

        val filePath = awsS3Adapter.saveImage(multipartToFile(file), imageType)

        return ImageUrlResponse(filePath)
    }

    private fun multipartToFile(multipartFile: MultipartFile?): File {

        if (multipartFile == null || multipartFile.isEmpty || multipartFile.originalFilename == null) {
            throw InvalidImageException
        }
        val file = File(multipartFile.originalFilename!!)

        try {
            FileOutputStream(file).use { outputStream -> outputStream.write(multipartFile.bytes) }
        } catch (e: IOException) {
            throw InvalidImageException
        }
        return file
    }

}