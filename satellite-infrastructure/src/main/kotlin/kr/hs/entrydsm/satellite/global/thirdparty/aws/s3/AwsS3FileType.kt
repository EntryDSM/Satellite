package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import software.amazon.awssdk.services.s3.model.ObjectCannedACL

enum class AwsS3FileType(
    val cannedAcl: ObjectCannedACL
) {
    PDF(ObjectCannedACL.AUTHENTICATED_READ),
    IMAGE(ObjectCannedACL.PUBLIC_READ)
}