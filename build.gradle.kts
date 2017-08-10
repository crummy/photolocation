allprojects {
    buildscript {
        val kotlin_version = "1.1.3-2"

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
