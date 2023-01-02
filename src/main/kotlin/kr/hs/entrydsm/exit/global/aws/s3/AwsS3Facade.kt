package kr.hs.entrydsm.exit.global.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import kr.hs.entrydsm.exit.global.exception.image.ImageExtensionInvalidException
import kr.hs.entrydsm.exit.global.exception.image.ImageNotFoundException

import org.springframework.stereotype.Component
import java.io.*
import java.util.*


@Component
class AwsS3Facade(
    private val amazonS3: AmazonS3,
    private val awsS3Properties: AwsS3Properties
) {

    fun saveImage(file: File, imageType: ImageType): String {
        val extension = getExtension(file)

        val folder = when(imageType) {
            ImageType.PROFILE -> awsS3Properties.profileFolder
            ImageType.DOCUMENT -> awsS3Properties.documentFolder
        }

        val filePath = folder + UUID.randomUUID().toString() + extension

        uploadImage(file, filePath)
        return awsS3Properties.url + filePath
    }

    private fun getExtension(file: File): String {

        val originalFilename = file.name
        val extension = originalFilename.substring(originalFilename.lastIndexOf("."))

        if (!(extension == ".jpg" || extension == ".jpeg" || extension == ".png" || extension == ".heic")) {
            throw ImageExtensionInvalidException
        }
        return extension
    }

    private fun uploadImage(file: File, filePath: String) {

        val bytes: ByteArray
        val objectMetadata: ObjectMetadata
        val byteArrayInputStream: ByteArrayInputStream

        try {
            val inputStream: InputStream = FileInputStream(file)
            bytes = IOUtils.toByteArray(inputStream)
            objectMetadata = objectMetadata(file, bytes)
            byteArrayInputStream = ByteArrayInputStream(bytes)
        } catch (e: IOException) {
            throw ImageNotFoundException
        }

        amazonS3.putObject(
            PutObjectRequest(
                awsS3Properties.bucket,
                filePath,
                byteArrayInputStream,
                objectMetadata
            ).withCannedAcl(CannedAccessControlList.AuthenticatedRead)
        )

        file.delete()
    }

    private fun objectMetadata(file: File, bytes: ByteArray): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = Mimetypes.getInstance().getMimetype(file)
        objectMetadata.contentLength = bytes.size.toLong()
        return objectMetadata
    }

}