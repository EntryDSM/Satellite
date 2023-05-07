package kr.hs.entrydsm.satellite.domain.document.dto

import kr.hs.entrydsm.satellite.domain.file.domain.DefaultImages

open class UpdateProfileImageRequest(
    profileImagePath: String?
) {
    val profileImagePath: String = profileImagePath ?: DefaultImages.USER_PROFILE
}