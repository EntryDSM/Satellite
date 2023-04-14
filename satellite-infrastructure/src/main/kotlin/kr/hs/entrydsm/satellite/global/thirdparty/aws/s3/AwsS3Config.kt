package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import org.springframework.stereotype.Component

@Component
class AwsS3Config {

    fun s3asyncClient() {
        val s3AsyncClient: S3AsyncClient = S3AsyncClient.crtBuilder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.US_EAST_1)
            .targetThroughputInGbps(20.0)
            .minimumPartSizeInBytes(8 * MB)
            .build()

        val transferManager: S3TransferManager = S3TransferManager.builder()
            .s3Client(s3AsyncClient)
            .build()
    }
}