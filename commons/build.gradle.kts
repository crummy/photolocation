plugins {
    kotlin("jvm")
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
}