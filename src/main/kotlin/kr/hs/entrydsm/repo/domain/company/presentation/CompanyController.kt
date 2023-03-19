package kr.hs.entrydsm.repo.domain.company.presentation

import kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanyPasswordChangeRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignInRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignUpRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.response.CompanyListResponse
import kr.hs.entrydsm.repo.domain.company.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RequestMapping("/company")
@RestController
class CompanyController(
    private val signInUseCase: CompanySignInUseCase,
    private val passwordChangeUseCase: CompanyPasswordChangeUseCase,
    private val queryCompanyUseCase: QueryCompanyUseCase,

    private val companySignUpUseCase: CompanySignUpUseCase,
    private val allowStandByCompanyUseCase: AllowStandbyCompanyUseCase,
    private val rejectStandByCompanyUseCase: RejectStandbyCompanyUseCase,
    private val queryStandbyCompanyUseCase: QueryStandbyCompanyUseCase,
) {

    @PostMapping("/auth")
    fun signIn(@ModelAttribute request: CompanySignInRequest): TokenResponse {
        return signInUseCase.execute(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/password")
    fun passwordChange(@ModelAttribute request: CompanyPasswordChangeRequest) {
        return passwordChangeUseCase.execute(request)
    }

    @GetMapping
    fun queryCompany(@ModelAttribute request: QueryCompanyRequest): CompanyListResponse {
        return queryCompanyUseCase.execute(request)
    }

    // TODO: 보류
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

}