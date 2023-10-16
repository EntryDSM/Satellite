plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"  
}

dependencies {

    implementation(project(":satellite-application"))

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // Oauth
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // Web
    runtimeOnly("javax.servlet:javax.servlet-api:4.0.1")
    runtimeOnly("org.glassfish.jaxb:jaxb-runtime")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("org.mariadb:r2dbc-mariadb:1.0.3")
    implementation("io.r2dbc:r2dbc-pool:1.0.0.RELEASE")
    implementation("io.r2dbc:r2dbc-spi:1.0.0.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.mongodb:mongodb-driver-async:3.12.12")

    // UUID
    implementation("com.fasterxml.uuid:java-uuid-generator:4.1.0")

    // AWS
    val awsSdkVersion = "2.21.0"
    implementation("software.amazon.awssdk:bom:$awsSdkVersion")
    implementation("software.amazon.awssdk:s3-transfer-manager:$awsSdkVersion")
    implementation("software.amazon.awssdk:s3:$awsSdkVersion")
    implementation("software.amazon.awssdk.crt:aws-crt:0.27.3")

    // AOP
    implementation("org.aspectj:aspectjweaver:1.9.7")

    // Logging
    implementation("io.sentry:sentry-spring-boot-starter:6.16.0")

}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-webmvc")
    }
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("org.springframework.data.relational.core.mapping.Table")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

noArg {
    annotation("org.springframework.data.relational.core.mapping.Table")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
