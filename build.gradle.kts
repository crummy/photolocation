
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.repositories

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:2.0.0")
    }
}

subprojects {
    buildscript {
        repositories {
            mavenCentral()
            jcenter()
        }
    }

    group = "com.malcolmcrum.photolocation"
    version = "1.0"

    tasks {
        "shadowJar"(ShadowJar::class) {
            baseName = "${project.name}-all"
            classifier = null
            version = null

            manifest {
                attributes.put("Main-Class", "org.jetbrains.ktor.jetty.DevelopmentHost")
            }
        }
    }
}

