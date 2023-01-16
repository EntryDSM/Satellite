package kr.hs.entrydsm.exit.common

import io.kotest.core.spec.Spec
import io.mockk.clearAllMocks

fun Spec.afterContainer() {
    afterContainer {
        clearAllMocks()
    }
}