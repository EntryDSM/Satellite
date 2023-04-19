package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.global.exception.InvalidExtensionException
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest
import java.io.File
import java.nio.file.Paths
import java.time.Duration
import java.util.*


@Component
class AwsS3Adapter(
    private val transferManager: S3TransferManager,
    private val s3Presigner: S3Presigner,
    private val awsS3Properties: AwsS3Properties
) : FilePort {

    override suspend fun savePdf(file: File): String {

        val extension = getExtension(file)
        if (extension != ".pdf") {
            throw InvalidExtensionException
        }

        val folder = awsS3Properties.documentFolder

        return "$folder/${file.name}"
            .also { uploadFile(file, it, AwsS3FileType.PDF) }
    }

    override suspend fun saveImage(file: File, imageType: ImageType): String {

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

    private suspend fun getExtension(file: File): String {
        val originalFilename = file.name
        return originalFilename.substring(originalFilename.lastIndexOf("."))
    }

    private suspend fun uploadFile(file: File, filePath: String, fileType: AwsS3FileType) {
        val uploadFileRequest = UploadFileRequest
            .builder()
            .putObjectRequest {
                it.bucket(awsS3Properties.bucket)
                    .key(filePath)
                    .acl(fileType.cannedAcl)
            }
            .source(Paths.get(filePath))
            .build()

        val fileUpload = transferManager.uploadFile(uploadFileRequest)
        fileUpload.completionFuture().join()
    }

    override suspend fun getPdfFileUrl(filePath: String): String {

        val getObjectPresignRequest = GetObjectPresignRequest
            .builder()
            .signatureDuration(Duration.ofHours(4))
            .getObjectRequest(
                GetObjectRequest
                    .builder()
                    .bucket(awsS3Properties.bucket)
                    .key(filePath)
                    .build()
            )
            .build()

        return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString()
    }

    override suspend fun getImageFileUrl(filePath: String): String {
        return awsS3Properties.url + filePath
    }

    override suspend fun getFileBaseUrl(): String = awsS3Properties.url

}