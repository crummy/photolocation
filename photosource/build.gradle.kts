import java.net.URI

plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "com.malcolmcrum.photosource.Application"
}

val ktor_version = "0.4.0-alpha-11"

repositories {
    maven { url = URI("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = URI("http://dl.bintray.com/kotlin/ktor") }
}

dependencies {
    compile("org.jetbrains.ktor:ktor-netty:${ktor_version}")
    compile("org.jetbrains.ktor:ktor-gson:${ktor_version}")
    compile("io.github.microutils:kotlin-logging:1.4.6")
    compile("ch.qos.logback:logback-classic:1.0.13")
    compile("com.github.salomonbrys.kodein:kodein:4.1.0")
    compile("com.drewnoakes:metadata-extractor:2.10.1")
    compile(kotlin("stdlib"))
    compile(project(":commons"))
    testCompile("junit", "junit", "4.12")
}