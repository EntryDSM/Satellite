package kr.hs.entrydsm.repo.domain.company.presentation

import java.util.UUID
import javax.validation.Valid
import kr.hs.entrydsm.repo.domain.auth.dto.response.TokenResponse
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanyPasswordChangeRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignInRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.CompanySignUpRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.request.QueryCompanyRequest
import kr.hs.entrydsm.repo.domain.company.presentation.dto.response.CompanyListResponse
import kr.hs.entrydsm.repo.domain.company.usecase.AllowStandbyCompanyUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.CompanyPasswordChangeUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.CompanySignInUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.CompanySignUpUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.QueryCompanyUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.QueryStandbyCompanyUseCase
import kr.hs.entrydsm.repo.domain.company.usecase.RejectStandbyCompanyUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
    private fun queryCompany(@ModelAttribute request: QueryCompanyRequest): CompanyListResponse {
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