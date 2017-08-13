import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import java.net.URI

allprojects {
    buildscript {
        repositories {
            mavenCentral()
        }
    }

    group = "com.malcolmcrum.photolocation"
    version = "1.0"

    plugins {
        kotlin("jvm")
    }

    repositories {
        maven { url = URI("http://download.osgeo.org/webdav/geotools/") }

        jcenter()
    }
}
