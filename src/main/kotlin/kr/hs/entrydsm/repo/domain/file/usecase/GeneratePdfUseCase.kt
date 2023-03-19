package kr.hs.entrydsm.repo.domain.file.usecase

import kr.hs.entrydsm.repo.domain.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.repo.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.repo.global.thirdparty.pdf.PdfAdapter

@ReadOnlyUseCase
class GeneratePdfUseCase(
    val pdfAdapter: PdfAdapter,
    val documentRepository: DocumentRepository
) {

    fun execute(): ByteArray {
        return pdfAdapter.generateGradeLibraryDocument(
            documentRepository.findAll()
        )
    }
}