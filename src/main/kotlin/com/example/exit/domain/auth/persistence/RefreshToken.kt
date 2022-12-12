package com.example.exit.domain.auth.persistence

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.UUID
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank

@RedisHash
class RefreshToken(

    @Id
    @Indexed
    val userId: UUID,

    @NotBlank
    val token: String,

    @TimeToLive
    val timeToLive: Long

)