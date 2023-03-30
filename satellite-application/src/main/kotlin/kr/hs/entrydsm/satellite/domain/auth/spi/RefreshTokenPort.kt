package kr.hs.entrydsm.satellite.domain.auth.spi

import kr.hs.entrydsm.satellite.domain.auth.domain.RefreshToken

interface RefreshTokenPort {
   fun queryByToken(token: String): RefreshToken?
}