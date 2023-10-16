plugins {
    kotlin("jvm") version "1.6.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
}

buildscript {
    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        version = KotlinVersion
    }

    apply {
        plugin("org.jetbrains.kotlin.kapt")
        version = KotlinVersion
    }

    dependencies {

        // Kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Coroutine
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.1")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")

        // Test
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.4.0")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.0")
        testImplementation("io.mockk:mockk:1.13.2")
    }
}

allprojects {
    group = "kr.hs.entrydsm.satellite"
    version = "0.0.1-SNAPSHOT"

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        compileJava {
            sourceCompatibility = JavaVersion.VERSION_17.majorVersion
        }

        test {
            useJUnitPlatform()
        }
    }

    repositories {
        mavenCentral()
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}