package kr.hs.entrydsm.exit.domain.company.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.domain.company.persistence.StandbyCompany
import kr.hs.entrydsm.exit.domain.company.persistence.repository.CompanyRepository
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull


internal class AllowStandbyCompanyUseCaseTest : DescribeSpec({

    val standbyCompanyRepository = mockk<StandbyCompanyRepository>()
    val companyRepository = mockk<CompanyRepository>()

    val allowStandByCompanyUseCase = AllowStandbyCompanyUseCase(standbyCompanyRepository, companyRepository)

    describe("allowStandByCompany") {

        val standByCompany = anyValueObject<StandbyCompany>()

        context("대기중인 회사의 id가 주어지면") {

            every { standbyCompanyRepository.findByIdOrNull(standByCompany.id) } returns standByCompany
            every { companyRepository.save(any()) } returnsArgument 0
            justRun { standbyCompanyRepository.delete(standByCompany) }

            it("승인된 회사로 저장한뒤 기존 정보를 삭제한다.") {

                allowStandByCompanyUseCase.execute(standByCompany.id)
                verify(exactly = 1) { companyRepository.save(any()) }
                verify(exactly = 1) { standbyCompanyRepository.delete(standByCompany) }
            }
        }
    }

    afterContainer()
})