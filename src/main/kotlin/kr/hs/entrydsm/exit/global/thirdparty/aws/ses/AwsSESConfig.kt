package kr.hs.entrydsm.exit.global.thirdparty.aws.ses

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AwsSESConfig(
    private val awsSESProperties: AwsSESProperties
) {
    @Bean
    fun amazonSimpleEmailService(): AmazonSimpleEmailServiceAsync {
        val credentials = BasicAWSCredentials(awsSESProperties.accessKey, awsSESProperties.secretKey)

        return AmazonSimpleEmailServiceAsyncClient.asyncBuilder()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(awsSESProperties.region)
            .build()
    }
}