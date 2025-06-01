plugins {
    java
    jacoco
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vavr:vavr:0.10.6")

    implementation("org.yaml:snakeyaml:2.4") {
        version {
            strictly("2.4")
        }
    }

    testImplementation(platform("org.junit:junit-bom:5.13.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    doFirst {
        jvmArgs = listOf(
            "-Xshare:off",  // Desativa o Class Data Sharing
            "-javaagent:${classpath.find { it.name.contains("byte-buddy-agent") }?.absolutePath}"
        )
    }

    testLogging {
        events("passed", "skipped", "failed")
    }
}


tasks.register<Test>("unitTests") {
    group = "verification"
    useJUnitPlatform {
        includeTags("unitTest")
    }
}

tasks.register<Test>("integrationTests") {
    group = "verification"
//    dependsOn("runFirstTest")
    useJUnitPlatform {
        includeTags("integrationTest")
    }
}

tasks.register<Test>("e2eTests") {
    group = "verification"
    useJUnitPlatform {
        includeTags("e2eTest")
    }
}