package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import kotlinx.coroutines.reactive.awaitSingle
import kr.hs.entrydsm.satellite.domain.file.domain.ImageType
import kr.hs.entrydsm.satellite.domain.file.spi.FilePort
import kr.hs.entrydsm.satellite.global.exception.InvalidExtensionException
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter.Extensions.HEIC
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter.Extensions.JPEG
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter.Extensions.JPG
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter.Extensions.PNG
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter.Extensions.SVG
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.model.UploadRequest
import java.time.Duration
import java.util.*


@Component
class AwsS3Adapter(
    private val s3TransferManager: S3TransferManager,
    private val s3Presigner: S3Presigner,
    private val awsS3Properties: AwsS3Properties
) : FilePort {

    suspend fun saveImage(monoFilePart: Mono<FilePart>,imageType: ImageType): Mono<String> {
        val folder = when (imageType) {
            ImageType.PROFILE -> awsS3Properties.profileFolder
            ImageType.DOCUMENT -> awsS3Properties.documentFolder
        }

        return monoFilePart.map { filePart ->
            val extension = filePart.filename().getExtension()
                .also {
                    if (!arrayOf(JPG,JPEG,PNG,HEIC,SVG).contains(it)) {
                        throw InvalidExtensionException
                    }
                }
            return@map "$folder/${UUID.randomUUID()}$extension".also { key ->
                uploadFile(
                    fileContent = filePart.content(),
                    key = key,
                    fileType = AwsS3FileType.IMAGE
                )
            }
        }
    }

    suspend fun savePdf(monoFilePart: Mono<Part>): String {
        val folder = awsS3Properties.pdfFolder
        return monoFilePart.map { filePart ->
            return@map "$folder/${UUID.randomUUID()}.pdf".also { key ->
                uploadFile(
                    fileContent = filePart.content(),
                    key = key,
                    fileType = AwsS3FileType.PDF
                )
            }
        }.awaitSingle()
    }

    private fun String.getExtension(): String {
        return substring(lastIndexOf(".")).lowercase()
    }

    private fun uploadFile(fileContent: Flux<DataBuffer>, key: String, fileType: AwsS3FileType) {
        println("AwsS3Adapter.uploadFile")
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(awsS3Properties.bucket)
            .key(key)
            .acl(fileType.cannedAcl)
            .build()

        s3TransferManager
            .upload(
                UploadRequest.builder()
                    .putObjectRequest(putObjectRequest)
                    .requestBody(AsyncRequestBody.fromPublisher(fileContent.map { it.asByteBuffer() }))
                    .build()
            ).completionFuture().get()
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

    override fun getFileBaseUrl(): String = awsS3Properties.url

    object Extensions {
        const val JPG = ".jpg"
        const val JPEG = ".jpeg"
        const val PNG = ".png"
        const val HEIC = ".heic"
        const val SVG = ".svg"
        const val PDF = ".pdf"
    }
}
