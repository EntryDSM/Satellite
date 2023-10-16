package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import software.amazon.awssdk.services.s3.model.ObjectCannedACL

// 기존엔 ACL 지정으로 PDF, Image 접근 권한을 다르게 줬으나 xquare 인프라 사용하면서 사라짐
enum class AwsS3FileType(
    val cannedAcl: ObjectCannedACL
) {
    PDF(ObjectCannedACL.AUTHENTICATED_READ),
    IMAGE(ObjectCannedACL.PUBLIC_READ)
}