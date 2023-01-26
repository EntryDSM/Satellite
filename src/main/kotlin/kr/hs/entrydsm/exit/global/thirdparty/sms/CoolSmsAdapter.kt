package kr.hs.entrydsm.exit.global.thirdparty.sms

import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.springframework.stereotype.Component

@Component
class CoolSmsAdapter(
    private val coolSmsProperties: CoolSmsProperties
) {

    fun sendAuthCode(phoneNumber: String, authCode: String) {

        val message = Message(coolSmsProperties.key, coolSmsProperties.secret)

        val params: HashMap<String, String> = HashMap()
        params["to"] = phoneNumber
        params["from"] = coolSmsProperties.phoneNumber
        params["type"] = "SMS"
        params["text"] = getBody(authCode)
        params["app_version"] = "app 1.0"

        try {
            message.send(params)
        } catch (e: CoolsmsException) {
            e.stackTrace
        }
    }

    private fun getBody(authCode: String): String {
        return "[exit] 인증번호 ${authCode}를 입력하세요."
    }
}