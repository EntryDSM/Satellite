package kr.hs.entrydsm.repo.domain.document.presentation.dto.request

import kr.hs.entrydsm.repo.global.util.RegexUtil
import org.hibernate.validator.constraints.Length
import org.intellij.lang.annotations.Pattern

data class QueryPagingInfoRequest(

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val grade: String,

    @field:Length(min = 1, max = 1)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val classNum: String,

    @field:Length(min = 2, max = 2)
    @field:Pattern(RegexUtil.NUMBER_EXP)
    val number: String
)