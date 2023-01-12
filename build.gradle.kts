import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.7.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // oauth
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // time base uuid
    implementation("com.fasterxml.uuid:java-uuid-generator:3.1.4")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DB
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.mongodb:mongodb-driver-sync:4.6.0")

    // querydsl
    implementation("com.querydsl:querydsl-mongodb:5.0.0")
    api("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0")

    // Fegin Client
    implementation("io.github.openfeign:feign-httpclient:11.9.1")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")

    // AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // cool sms
    implementation("net.nurigo:javaSDK:2.2")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")

    testImplementation("io.mockk:mockk:1.13.2")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

kapt {
    annotationProcessor("org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor")
    annotationProcessor("com.querydsl.apt.jpa.JPAAnnotationProcessor")
}

querydsl {
    springDataMongo = true
    jpa = true
    library = "com.querydsl:querydsl-apt:5.0.0"
    querydslSourcesDir = "$projectDir/build/generated"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
