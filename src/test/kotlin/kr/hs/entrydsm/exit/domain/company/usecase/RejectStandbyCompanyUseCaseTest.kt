package kr.hs.entrydsm.exit.domain.company.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kr.hs.entrydsm.exit.common.AnyValueObjectGenerator.anyValueObject
import kr.hs.entrydsm.exit.common.afterContainer
import kr.hs.entrydsm.exit.domain.company.persistence.StandbyCompany
import kr.hs.entrydsm.exit.domain.company.persistence.repository.StandbyCompanyRepository
import org.springframework.data.repository.findByIdOrNull

internal class RejectStandbyCompanyUseCaseTest : DescribeSpec({

    val standbyCompanyRepository = mockk<StandbyCompanyRepository>()

    val rejectStandByCompanyUseCase = RejectStandbyCompanyUseCase(standbyCompanyRepository)

    describe("rejectStandbyCompany") {

        val standByCompany = anyValueObject<StandbyCompany>()

        context("대기중인 회사의 id가 주어지면") {

            every { standbyCompanyRepository.findByIdOrNull(standByCompany.id) } returns standByCompany
            justRun { standbyCompanyRepository.delete(standByCompany) }

            it("정보를 삭제한다.") {

                rejectStandByCompanyUseCase.execute(standByCompany.id)
                verify(exactly = 1) { standbyCompanyRepository.delete(standByCompany) }
            }
        }
    }

    afterContainer()
})