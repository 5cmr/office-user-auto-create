plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}
//buildscript {
//    repositories {
//        maven {
//            url = uri("https://plugins.gradle.org/m2/")
//        }
//    }
//
//    dependencies {
////        classpath(kotlin("gradle-plugin"))
//        classpath("com.github.johnrengelman:shadow:8.1.1")
//    }
//}
allprojects {
    repositories {
        mavenCentral()
//        maven { url = uri("https://plugins.gradle.org/m2/") }
//        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
//        maven(url = "https://kotlin.bintray.com/ktor")
//        maven(url = "https://dl.bintray.com/kotlin/exposed")
//        maven(url = "https://jitpack.io")
//        maven(url = "https://www.myget.org/F/ifish/maven")

    }
}