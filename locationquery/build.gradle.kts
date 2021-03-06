
import org.gradle.kotlin.dsl.*
import java.net.URI

plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "com.malcolmcrum.locationquery.Application"
}

val ktor_version = "0.4.0-alpha-14"

repositories {
    maven { url = URI("http://download.osgeo.org/webdav/geotools/") }
    maven { url = URI("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = URI("http://dl.bintray.com/kotlin/ktor") }
    jcenter()
}

dependencies {
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("io.github.microutils:kotlin-logging:1.4.6")
    compile("org.geotools:gt-cql:17.2")
    compile("org.geotools:gt-shapefile:17.2")
    compile("org.jetbrains.ktor:ktor-jetty:$ktor_version")
    compile("org.jetbrains.ktor:ktor-gson:$ktor_version")
    compile("com.github.salomonbrys.kodein:kodein:4.1.0")
    compile(project(":commons"))
    compile(kotlin("stdlib"))
    testCompile("junit:junit:4.12")
}