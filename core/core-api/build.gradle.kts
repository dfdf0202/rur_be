plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("com.auth0:jwks-rsa:0.20.2")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation(project(":storage:db-core"))
}
