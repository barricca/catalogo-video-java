//plugins {
//    `java-conventions`
//    `jacoco-report-aggregation`
//    application
//    id("org.springframework.boot") version "3.2.2"
//    id("io.spring.dependency-management") version "1.1.4"
//}
//
//group = "tech.wbrq.catalogo.infrastructure"
//
//tasks.bootJar {
//    archiveBaseName.set("application.jar")
//    destinationDirectory.set(layout.buildDirectory.dir("libs"))
//}
//
//dependencies {
//    implementation(project(":domain"))
//    implementation(project(":application"))
//
//    implementation("com.google.guava:guava:33.0.0-jre")
//
//    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
//
//    implementation("org.springframework.boot:spring-boot-starter-web") {
//        exclude(module = "spring-boot-starter-tomcat")
//    }
//
//    implementation("org.springframework.boot:spring-boot-starter-undertow")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//
//    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
//
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.security:spring-security-test")
//
//    testImplementation(project(path = ":domain", configuration = "testClasses"))
//    testImplementation("org.testcontainers:testcontainers:1.19.3") {
//        exclude(module = "commons-compress")
//    }
//    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
//
//    testImplementation("org.apache.commons:commons-compress:1.25.0")
//}
//
//tasks.named<JacocoReport>("testCodeCoverageReport") {
//    reports {
//        xml.required.set(true)
//        xml.outputLocation.set(layout.buildDirectory.file("${rootProject.rootDir}/build/reports/jacoco/test/jacocoTestReport.xml"))
//
//        html.required.set(true)
//        html.outputLocation.set(layout.buildDirectory.dir("${rootProject.rootDir}/build/reports/jacoco/test/"))
//    }
//}
//
//tasks.named("jacocoTestReport") {
//    dependsOn(tasks.named("testCodeCoverageReport"))
//}