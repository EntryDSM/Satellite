package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import kr.hs.entrydsm.satellite.global.thirdparty.aws.config.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.SizeConstant.MB

@Component
class AwsS3Config(
    private val awsProperties: AwsProperties
) {

    // https://github.com/aws/aws-sdk-java-v2/issues/1750
    @Bean
    fun s3TransferManager(): S3TransferManager {
        val s3AsyncClient = S3AsyncClient
            .crtBuilder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.of(awsProperties.region))
            .targetThroughputInGbps(20.0)
            .minimumPartSizeInBytes(8 * MB)
            .build()

        return S3TransferManager.builder()
            .s3Client(s3AsyncClient)
            .build()
    }

    @Bean
    fun s3Presigner(): S3Presigner =
        S3Presigner
            .builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.of(awsProperties.region))
            .build()
}