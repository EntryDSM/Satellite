package kr.hs.entrydsm.exit.domain.document.presentation

import kr.hs.entrydsm.exit.domain.document.presentation.dto.request.*
import kr.hs.entrydsm.exit.domain.document.presentation.dto.response.CreateDocumentResponse
import kr.hs.entrydsm.exit.domain.document.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/documents")
class DocumentController(
    private val createDocumentService: CreateDocumentService
) {
 
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createDocument(@RequestBody request: CreateDocumentRequest): CreateDocumentResponse {
        return createDocumentService.execute(request)
    }

}
