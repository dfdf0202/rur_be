plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    implementation("com.zaxxer:HikariCP")
    implementation("com.mysql:mysql-connector-j:8.0.33")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

val querydslDir = "build/generated/querydsl"

sourceSets["main"].java {
    srcDir(querydslDir)
}

tasks.withType<JavaCompile> {
    options.annotationProcessorGeneratedSourcesDirectory = file(querydslDir)
}

kapt {
    arguments {
        arg("querydsl.entityAccessors", "true")
        arg("querydsl.createDefaultVariable", "true")
    }
}