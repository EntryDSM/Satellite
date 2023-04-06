package kr.hs.entrydsm.satellite.global.thirdparty.aws.s3

import com.amazonaws.services.s3.model.CannedAccessControlList

enum class AwsS3FileType(
    val cannedAcl: CannedAccessControlList
) {
    PDF(CannedAccessControlList.AuthenticatedRead),
    IMAGE(CannedAccessControlList.PublicRead)
}