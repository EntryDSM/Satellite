package kr.hs.entrydsm.satellite.domain.file.usecase

import kr.hs.entrydsm.satellite.domain.document.persistence.repository.DocumentRepository
import kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase
import kr.hs.entrydsm.satellite.common.thirdparty.pdf.PdfAdapter

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