package kr.hs.entrydsm.exit.domain.company.presentation

import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.CompanySignUpRequest
import kr.hs.entrydsm.exit.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.exit.domain.company.presentation.dto.response.CompanyListResponse
import kr.hs.entrydsm.exit.domain.company.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RequestMapping("/company")
@RestController
class CompanyController(
    private val companySignUpUseCase: CompanySignUpUseCase,
    private val allowStandByCompanyUseCase: AllowStandbyCompanyUseCase,
    private val rejectStandByCompanyUseCase: RejectStandbyCompanyUseCase,
    private val queryStandbyCompanyUseCase: QueryStandbyCompanyUseCase,
    private val queryCompanyUseCase: QueryCompanyUseCase,
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

    @GetMapping("/standby")
    fun queryStandbyCompany(@ModelAttribute request: QueryCompanyRequest): CompanyListResponse {
        return queryStandbyCompanyUseCase.execute(request)
    }

    @GetMapping("/")
    fun queryCompany(@ModelAttribute request: QueryCompanyRequest): CompanyListResponse {
        return queryStandbyCompanyUseCase.execute(request)
    }
}