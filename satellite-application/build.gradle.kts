plugins {
    kotlin("plugin.noarg") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
}

dependencies {

}

allOpen {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
    annotation("kr.hs.entrydsm.satellite.common.annotation.UseCase")
    annotation("kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase")
}

noArg {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
    annotation("kr.hs.entrydsm.satellite.common.annotation.UseCase")
    annotation("kr.hs.entrydsm.satellite.common.annotation.ReadOnlyUseCase")
}