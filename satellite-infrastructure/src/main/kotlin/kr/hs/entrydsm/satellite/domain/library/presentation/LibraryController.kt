package kr.hs.entrydsm.satellite.domain.library.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import javax.validation.constraints.NotNull
import kotlinx.coroutines.reactor.awaitSingle
import kr.hs.entrydsm.satellite.domain.library.domain.AccessRight
import kr.hs.entrydsm.satellite.domain.library.domain.DocumentIndex
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentDetailResponse
import kr.hs.entrydsm.satellite.domain.library.dto.LibraryDocumentIndexResponse
import kr.hs.entrydsm.satellite.domain.library.dto.ManagerQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.dto.StudentQueryLibraryResponse
import kr.hs.entrydsm.satellite.domain.library.usecase.CreateLibraryDocumentUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.ManagerQueryLibraryUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.QueryLibraryDocumentDetailUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.QueryLibraryDocumentIndexUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.StudentQueryLibraryUseCase
import kr.hs.entrydsm.satellite.domain.library.usecase.UpdateLibraryDocumentAccessRightUseCase
import kr.hs.entrydsm.satellite.global.thirdparty.aws.s3.AwsS3Adapter
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FormFieldPart
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@RequestMapping("/library")
@RestController
class LibraryController(
    private val managerQueryLibraryUseCase: ManagerQueryLibraryUseCase,
    private val studentQueryLibraryUseCase: StudentQueryLibraryUseCase,
    private val queryLibraryDocumentIndexUseCase: QueryLibraryDocumentIndexUseCase,
    private val queryLibraryDocumentDetailUseCase: QueryLibraryDocumentDetailUseCase,
    private val updateLibraryDocumentAccessRightUseCase: UpdateLibraryDocumentAccessRightUseCase,
    private val createLibraryDocumentUseCase: CreateLibraryDocumentUseCase,
    private val s3Adapter: AwsS3Adapter,
    private val objectMapper: ObjectMapper
) {

    @GetMapping("/teacher")
    suspend fun managerQueryLibrary(
        @RequestParam(name = "year") year: Int?
    ): ManagerQueryLibraryResponse {
        return managerQueryLibraryUseCase.execute(year)
    }

    @GetMapping("/student")
    suspend fun studentQueryLibrary(
        @RequestParam(name = "year") year: Int?
    ): StudentQueryLibraryResponse {
        return studentQueryLibraryUseCase.execute(year)
    }

    @GetMapping("/{library-document-id}/index")
    suspend fun queryLibraryDocumentIndex(
        @PathVariable("library-document-id") libraryDocumentId: UUID,
    ): LibraryDocumentIndexResponse {
        return queryLibraryDocumentIndexUseCase.execute(libraryDocumentId)
    }

    @GetMapping("/public/{library-document-id}")
    suspend fun queryLibraryDocument(
        @PathVariable("library-document-id") libraryDocumentId: UUID
    ): LibraryDocumentDetailResponse {
        return queryLibraryDocumentDetailUseCase.execute(libraryDocumentId)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{library-document-id}/access-right")
    suspend fun updateLibraryDocumentAccessRight(
        @PathVariable("library-document-id") libraryDocumentId: UUID,
        @RequestParam(name = "access_right") accessRight: AccessRight
    ) {
        return updateLibraryDocumentAccessRightUseCase.execute(
            libraryDocumentId = libraryDocumentId,
            accessRight = accessRight
        )
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun saveLibraryDocument(
        @RequestParam(name = "grade") @NotNull grade: Int,
        @RequestPart("pdf") monoPdfPart: Mono<FormFieldPart>,
        @RequestPart("index") monoIndexJsonPart: Mono<FormFieldPart>
    ) {
        createLibraryDocumentUseCase.execute(
            documentIndex = monoIndexJsonPart.toDocumentIndexList(),
            filePath = s3Adapter.savePdf(monoPdfPart),
            grade = grade
        )
    }

    suspend fun Mono<FormFieldPart>.toDocumentIndexList(): List<DocumentIndex> = flatMap { part ->
        DataBufferUtils.join(part.content())
            .map parse@{ dataBuffer: DataBuffer ->
                val bytes = ByteArray(dataBuffer.readableByteCount())
                dataBuffer.read(bytes)

                data class IndexJsonValue(val data: List<DocumentIndex>)
                return@parse objectMapper.readValue(bytes,IndexJsonValue::class.java).data
                    .also { DataBufferUtils.release(dataBuffer) }
            }
    }.awaitSingle()

}