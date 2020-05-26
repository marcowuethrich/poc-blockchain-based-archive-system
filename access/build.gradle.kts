import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.processes") version "0.5.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.2.0"
}

apply {
    plugin("org.springframework.boot")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

openApi {
    apiDocsUrl.set("http://localhost:8001/v3/api-docs")
    outputDir.set(file("../docs/open-api"))
    outputFileName.set("access-swagger.json")
}

dependencies {
    compile(project(":shared"))

    implementation("org.springdoc:springdoc-openapi-ui:${property("springdocVersion")}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${property("springdocVersion")}")
}

tasks.getByName<BootJar>("bootJar") {
    launchScript()
}
