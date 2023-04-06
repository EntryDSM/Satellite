package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.global.exception.InvalidExtensionException
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.File
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Component
class AwsS3Adapter(
    private val amazonS3: AmazonS3,
    private val awsS3Properties: AwsS3Properties,
) : FilePort {

    override fun savePdf(file: File): String {

        val extension = getExtension(file)
        if (extension != ".pdf") {
            throw InvalidExtensionException
        }

        val folder = awsS3Properties.documentFolder

        return "$folder/${file.name}"
            .also { uploadFile(file, it, AwsS3FileType.PDF) }
    }

    override fun saveImage(file: File, imageType: ImageType): String {

        val extension = getExtension(file)
        if (!(extension == ".jpg" || extension == ".jpeg" || extension == ".png" || extension == ".heic")) {
            throw InvalidExtensionException
        }

        val folder = when (imageType) {
            ImageType.PROFILE -> awsS3Properties.profileFolder
            ImageType.DOCUMENT -> awsS3Properties.documentFolder
        }

        return "$folder/${UUID.randomUUID()}$extension"
            .also { uploadFile(file, it, AwsS3FileType.IMAGE) }
    }

    private fun getExtension(file: File): String {
        val originalFilename = file.name
        return originalFilename.substring(originalFilename.lastIndexOf("."))
    }

    private fun uploadFile(file: File, filePath: String, fileType: AwsS3FileType) {
        val bytes = file.readBytes()
        amazonS3.putObject(
            PutObjectRequest(
                awsS3Properties.bucket,
                filePath,
                ByteArrayInputStream(bytes),
                ObjectMetadata().apply {
                    contentType = Mimetypes.getInstance().getMimetype(file)
                    contentLength = bytes.size.toLong()
                }
            ).withCannedAcl(fileType.cannedAcl)
        )
        file.delete()
    }

    override fun getPdfFileUrl(filePath: String): String {
        val request = GeneratePresignedUrlRequest(awsS3Properties.bucket, filePath)
            .withMethod(HttpMethod.GET)
            .withExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(4)))

        return amazonS3.generatePresignedUrl(request).toString()
    }

    override fun getImageFileUrl(filePath: String): String {
        return awsS3Properties.url + filePath
    }

    override fun getFileBaseUrl(): String = awsS3Properties.url

}