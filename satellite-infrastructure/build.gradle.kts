plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

dependencies {

    implementation(project(":satellite-application"))

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

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.mongodb:mongodb-driver-sync:4.6.0")

    // querydsl
    implementation("com.querydsl:querydsl-mongodb:5.0.0")
    api("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0")

    // feign Client
    implementation("io.github.openfeign:feign-httpclient:11.9.1")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")

    // AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation("com.amazonaws:aws-java-sdk-ses:1.12.144")

    // pdf
    implementation("org.thymeleaf:thymeleaf")
    implementation("com.itextpdf:html2pdf:4.0.3")

}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

noArg {
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

tasks.getByName<Jar>("jar") {
    enabled = false
}