package kr.hs.entrydsm.satellite.domain.major.presentation

import kr.hs.entrydsm.satellite.domain.major.dto.MajorListResponse
import kr.hs.entrydsm.satellite.domain.major.presentation.dto.request.CreateMajorRequest
import kr.hs.entrydsm.satellite.domain.major.usecase.CreateMajorUseCase
import kr.hs.entrydsm.satellite.domain.major.usecase.DeleteMajorUseCase
import kr.hs.entrydsm.satellite.domain.major.usecase.QueryMajorListUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/major")
@RestController
class MajorController(
    private val createMajorUseCase: CreateMajorUseCase,
    private val deleteMajorUseCase: DeleteMajorUseCase,
    private val queryMajorUseCase: QueryMajorListUseCase
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    suspend fun createMajor(@RequestBody request: CreateMajorRequest) {
        createMajorUseCase.execute(request.majorName)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{major-id}")
    fun deleteMajor(@PathVariable("major-id") majorId: UUID) {
        deleteMajorUseCase.execute(majorId)
    }

    @GetMapping
    suspend fun queryMajor(@RequestParam(value = "name", defaultValue = "") name: String): MajorListResponse {
        return queryMajorUseCase.execute(name)
    }
}