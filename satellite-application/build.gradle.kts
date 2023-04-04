plugins {
    kotlin("plugin.noarg") version "1.5.30"
    kotlin("plugin.allopen") version "1.5.30"
}

dependencies {
}

allOpen {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
}

noArg {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
}