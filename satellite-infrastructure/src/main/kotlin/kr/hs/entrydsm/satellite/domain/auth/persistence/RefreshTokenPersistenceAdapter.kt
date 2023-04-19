package kr.hs.entrydsm.satellite.domain.auth.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kr.hs.entrydsm.satellite.common.annotation.Adapter
import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken
import kr.hs.entrydsm.satellite.domain.auth.spi.RefreshTokenPort
import kr.hs.entrydsm.satellite.global.exception.InternalServerError
import org.springframework.data.redis.core.ReactiveRedisOperations
import java.time.Duration

@Adapter
class RefreshTokenPersistenceAdapter(
    private val reactiveRedisOperations: ReactiveRedisOperations<String, Any>,
    private val objectMapper: ObjectMapper
) : RefreshTokenPort {

    override suspend fun save(refreshToken: RefreshToken): RefreshToken {

        val isSaveSuccess = reactiveRedisOperations
            .opsForValue()
            .set(
                refreshToken.token,
                objectMapper.convertValue<RefreshTokenEntity>(refreshToken),
                Duration.ofSeconds(refreshToken.timeToLive)
            ).awaitSingle()

        if (!isSaveSuccess) {
            throw InternalServerError
        }

        return refreshToken
    }

    override suspend fun queryByToken(token: String): RefreshToken? {

        val refreshToken = reactiveRedisOperations
            .opsForValue()
            .get(token)
            .awaitSingleOrNull()

        return refreshToken?.let {
            objectMapper.convertValue<RefreshTokenEntity>(it)
        }
    }
}