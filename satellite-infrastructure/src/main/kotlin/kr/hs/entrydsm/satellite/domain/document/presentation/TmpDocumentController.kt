package kr.hs.entrydsm.satellite.domain.document.presentation

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kr.hs.entrydsm.satellite.domain.auth.spi.SecurityPort
import kr.hs.entrydsm.satellite.domain.student.domain.Student
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@org.springframework.data.mongodb.core.mapping.Document("test")
data class TmpDocumentEntity(
    @get:JvmName("getIdentifier")
    var id: String?,
    val year: String?,
    var isDeleted: String?,
    var status: String?,
    var writer: String?,
    var introduce: String?,
    var skillSet: String?,
    var projectList: String?,
    var awardList: String?,
    var certificateList: String?,
    var activiryList: String?
)

interface TmpDocumentRepository : ReactiveMongoRepository<TmpDocumentEntity, String> {
    fun findByWriterLike(like: String): Mono<TmpDocumentEntity>
}

@RequestMapping("/document")
@RestController
class TmpDocumentController(
    private val securityPort: SecurityPort,
    private val tmpDocumentRepository: TmpDocumentRepository
) {
    data class TmpResponse(
        var writer: String?,
        var introduce: String?,
        var skillSet: String?,
        var projectList: String?,
        var awardList: String?,
        var certificateList: String?,
        var activityList: String?
    ) {
        constructor(tmp: TmpDocumentEntity?) : this(
            writer = tmp?.writer,
            introduce = tmp?.introduce,
            skillSet = tmp?.skillSet,
            projectList = tmp?.projectList,
            awardList = tmp?.awardList,
            certificateList = tmp?.certificateList,
            activityList = tmp?.activiryList
        )

        fun formatting(student: Student): TmpResponse {
            return TmpResponse(
                formatString(writer, student),
                formatString(introduce, student),
                formatString(skillSet, student),
                formatString(projectList, student),
                formatString(awardList, student),
                formatString(certificateList, student),
                formatString(activityList, student)
            )
        }
        private fun formatString(str: String?, student: Student): String? {
            if (str == null) return null
            var str = str
            str = str.replace("urls=[],", "")
            str = str.replace("grade=${student.grade}, classNum=${student.classNum}, number=${student.number}", "")
            str = str.replace("com.intellij.database.remote.jdbc.helpers.MongoJdbcHelper\$MongoBinaryValue", "")
            str = str.replace(" 00:00:00 KST 2023", "")
            str = str.replace(" 00:00:00 KST 2022", "")
            str = str.replace(Regex(", [a-z]+\\s*"), " \n ")
            str = str.replace(", {", " \n ")
            str = str.replace("{", " \n ")
            str = str.replace("}", " \n ")
            return str
        }
    }

    @GetMapping("/tmp")
    suspend fun tmp(): TmpResponse {
        val student = securityPort.getCurrentStudent()
        val tmpDocument = tmpDocumentRepository
            .findByWriterLike("grade=${student.grade}, classNum=${student.classNum}, number=${student.number},")
            .awaitFirstOrNull()
        return TmpResponse(tmpDocument).formatting(student)
    }
}