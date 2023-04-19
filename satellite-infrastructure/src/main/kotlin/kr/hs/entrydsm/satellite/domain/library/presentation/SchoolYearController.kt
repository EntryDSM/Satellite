package kr.hs.entrydsm.satellite.domain.library.presentation

import kr.hs.entrydsm.satellite.domain.library.usecase.ChangeSchoolYearUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotNull

@RequestMapping("/school-year")
@RestController
class SchoolYearController(
    private val changeSchoolYearUseCase: ChangeSchoolYearUseCase
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    suspend fun changeSchoolYear(
        @RequestParam(name = "year") @NotNull year: Int?,
        @RequestParam(name = "secret") @NotNull secret: String?
    ) {
        return changeSchoolYearUseCase.execute(year!!, secret!!)
    }
}