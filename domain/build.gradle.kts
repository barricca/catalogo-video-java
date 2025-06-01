plugins {
    `java-conventions`
    `java-library`
}

group = "tech.wbrq.catalogo.domain"

dependencies {
    testImplementation("net.datafaker:datafaker:2.0.2")
}

configurations {
    create("testClasses") {
        extendsFrom(configurations.testImplementation.get())
    }
}

tasks.register<Jar>("testJar") {
    archiveClassifier.set("test")
    from(sourceSets.test.get().output)
}

artifacts {
    add("testClasses", tasks.named<Jar>("testJar"))
}