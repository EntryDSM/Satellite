package kr.hs.entrydsm.repo.global.thirdparty.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.ByteArrayInputStream
import java.io.File
import java.util.UUID
import kr.hs.entrydsm.repo.global.exception.file.ImageExtensionInvalidException
import kr.hs.entrydsm.repo.global.exception.file.PdfExtensionInvaildException
import org.springframework.stereotype.Component

@Component
class AwsS3Adapter(
    private val amazonS3: AmazonS3,
    private val awsS3Properties: AwsS3Properties,
) {

    fun savePdf(file: File): String {

        val extension = getExtension(file)
        if (extension != ".pdf") {
            throw PdfExtensionInvaildException
        }

        val folder = awsS3Properties.documentFolder

        return "$folder/${file.name}$extension"
            .also { uploadFile(file, it) }
    }

    fun saveImage(file: File, imageType: ImageType): String {

        val extension = getExtension(file)
        if (!(extension == ".jpg" || extension == ".jpeg" || extension == ".png" || extension == ".heic")) {
            throw ImageExtensionInvalidException
        }

        val folder = when (imageType) {
            ImageType.PROFILE -> awsS3Properties.profileFolder
            ImageType.DOCUMENT -> awsS3Properties.documentFolder
        }

        return "$folder/${UUID.randomUUID()}$extension"
            .also { uploadFile(file, it) }
    }

    private fun getExtension(file: File): String {
        val originalFilename = file.name
        return originalFilename.substring(originalFilename.lastIndexOf("."))
    }

    private fun uploadFile(file: File, filePath: String) {
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
            ).withCannedAcl(CannedAccessControlList.AuthenticatedRead)
        )
        file.delete()
    }
}