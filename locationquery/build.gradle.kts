import java.net.URI

plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "com.malcolmcrum.locationquery.Application"
}

val ktor_version = "0.4.0-alpha-11"

repositories {
    maven { url = URI("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = URI("http://dl.bintray.com/kotlin/ktor") }
}

dependencies {
    compile("de.westnordost:osmapi:1.7")
    compile("org.jetbrains.ktor:ktor-netty:${ktor_version}")
    compile("org.jetbrains.ktor:ktor-gson:${ktor_version}")
    compile("io.github.microutils:kotlin-logging:1.4.6")
    compile("com.github.salomonbrys.kodein:kodein:4.1.0")
    compile(project(":commons"))
    compile(kotlin("stdlib"))
}