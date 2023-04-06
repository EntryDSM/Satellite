plugins {
    kotlin("plugin.noarg") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
}

dependencies {

}

allOpen {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
}

noArg {
    annotation("kr.hs.entrydsm.satellite.common.annotation.Aggregate")
}