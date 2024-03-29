rootProject.name = "otus-java-2023"


pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
    }
}
include("hw01-gradle")
include("hw02-collections")
include("hw03-annotations")
include("hw04-gc")
include("hw05-proxy")
include("hw06-patterns")
include("hw07-patterns")
include("hw09-jdbc:demo")
include("hw09-jdbc:homework")
