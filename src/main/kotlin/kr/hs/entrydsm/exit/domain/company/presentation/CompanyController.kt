package kr.hs.entrydsm.exit.domain.company.presentation

import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanySignUpRequest
import kr.hs.entrydsm.exit.domain.company.usecase.AllowStandbyCompanyUseCase
import kr.hs.entrydsm.exit.domain.company.usecase.CompanySignUpUseCase
import kr.hs.entrydsm.exit.domain.company.usecase.RejectStandbyCompanyUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RequestMapping("/company")
@RestController
class CompanyController(
    private val companySignUpUseCase: CompanySignUpUseCase,
    private val allowStandByCompanyUseCase: AllowStandbyCompanyUseCase,
    private val rejectStandByCompanyUseCase: RejectStandbyCompanyUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: CompanySignUpRequest) {
        companySignUpUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/standby/{standby-company-id}")
    fun allowStandByCompany(@PathVariable("standby-company-id") standbyCompanyId: UUID) {
        allowStandByCompanyUseCase.execute(standbyCompanyId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/standby/{standby-company-id}")
    fun rejectStandByCompany(@PathVariable("standby-company-id") standbyCompanyId: UUID) {
        rejectStandByCompanyUseCase.execute(standbyCompanyId)
    }
}