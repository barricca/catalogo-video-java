plugins {
    `java-conventions`
    `jacoco-report-aggregation`
    application
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "tech.wbrq.catalogo.infrastructure"

tasks.bootJar {
    archiveBaseName.set("application.jar")
    destinationDirectory.set(layout.buildDirectory.dir("libs"))
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("com.google.guava:guava:33.4.8-jre")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")

    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-tomcat")
    }

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")

    testImplementation(project(path = ":domain", configuration = "testClasses"))
    testImplementation("org.testcontainers:testcontainers:1.21.1") {
        exclude(module = "commons-compress")
    }
    testImplementation("org.testcontainers:elasticsearch:1.21.1")
    testImplementation("org.testcontainers:junit-jupiter:1.21.1")

    testImplementation("org.apache.commons:commons-compress:1.27.1")
}

tasks.named<JacocoReport>("testCodeCoverageReport") {
    reports {
        xml.required.set(true)
        xml.outputLocation.set(
            layout.buildDirectory.file(
                "${rootProject.rootDir}/build/reports/jacoco/test/jacocoTestReport.xml"
            )
        )

        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("${rootProject.rootDir}/build/reports/jacoco/test/"))
    }
}

tasks.named("jacocoTestReport") {
    dependsOn(tasks.named("testCodeCoverageReport"))
}