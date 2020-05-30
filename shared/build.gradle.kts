plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
}
