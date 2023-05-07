package kr.hs.entrydsm.satellite.domain.document.presentation.dto.request

import kr.hs.entrydsm.satellite.domain.document.dto.UpdateProfileImageRequest
import org.hibernate.validator.constraints.Length

class UpdateProfileImageWebRequest(

    @Length(max = 255)
    profileImagePath: String?

): UpdateProfileImageRequest(profileImagePath)