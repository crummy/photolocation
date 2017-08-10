allprojects {
    buildscript {
        val kotlin_version = "1.1.4-eap-54-IJ2017.2-1"

        repositories {
            mavenCentral()
        }
        dependencies {
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        }
    }

    group = "com.malcolmcrum.photolocation"
    version = "1.0"

    plugins {
        kotlin("jvm")
    }

    repositories {
        jcenter()
    }
}
