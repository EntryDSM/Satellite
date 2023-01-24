package kr.hs.entrydsm.exit.domain.common.annotation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
@Transactional(readOnly = true)
annotation class ReadOnlyUseCase