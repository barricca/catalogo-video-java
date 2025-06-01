plugins {
    `java-conventions`
    `java-library`
}

group = "tech.wbrq.catalogo.application"

dependencies {
    implementation(project(":domain"))

    testImplementation(project(":domain", configuration = "testClasses"))
}