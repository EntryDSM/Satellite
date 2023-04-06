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

        // kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.5")

        testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
        testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
        testImplementation("io.kotest:kotest-extensions-spring:4.4.3")

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