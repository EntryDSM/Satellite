package kr.hs.entrydsm.repo.global.thirdparty.aws.ses

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.MessageRejectedException
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest
import com.fasterxml.jackson.databind.ObjectMapper
import kr.hs.entrydsm.repo.domain.auth.constant.MailType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AwsSESAdapter(
    private val objectMapper: ObjectMapper,
    private val amazonSimpleEmailServiceAsync: AmazonSimpleEmailServiceAsync
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun sendMail(email: String, mailType: kr.hs.entrydsm.repo.domain.auth.constant.MailType, params: Map<String, String>) {

        val request = SendTemplatedEmailRequest()
            .withDestination(Destination().withToAddresses(email))
            .withTemplate(mailType.templateName)
            .withSource("$UTF_8_ENCODED_SOURCE_NAME <noreply@entrydsm.hs.kr>")
            .withTemplateData(paramToJson(params))
        try {
            val result = amazonSimpleEmailServiceAsync.sendTemplatedEmailAsync(request)
            logger.info("sendMail result : {}", result)
        } catch (_: MessageRejectedException) { }
    }

    private fun paramToJson(params: Map<String, String?>): String {
        var data: String = objectMapper.writeValueAsString(params)
        data = data.replace("\"".toRegex(), "\\\"")
        return data
    }

    companion object {
        private const val UTF_8_ENCODED_SOURCE_NAME = "=?utf-8?B?7J6F7ZWZ7KCE7ZiV7Iuc7Iqk7YWc?="
    }
}