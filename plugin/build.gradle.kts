plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "2.0.0"
}
group = "com.japicraft"
version = "1.7"
repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
gradlePlugin {
    website.set("https://github.com/jirisitera/hytale-run-task")
    vcsUrl.set("https://github.com/jirisitera/hytale-run-task")
    plugins {
        create("HytaleRunTask") {
            id = "com.japicraft.hytale"
            implementationClass = "com.japicraft.HytalePlugin"
            displayName = "Hytale Run Task"
            description = "Gradle plugin for running a development Hytale server."
            tags.set(listOf("hytale"))
        }
    }
}
