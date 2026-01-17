package com.japicraft

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction

abstract class RunTask : JavaExec() {
    @get:Input
    abstract val runPath: Property<String>
    @get:Input
    abstract val buildTask: Property<String>
    @get:Input
    abstract val buildLocation: Property<String>
    @get:Input
    abstract val xmx: Property<String>
    @get:Input
    abstract val xms: Property<String>
    @TaskAction
    fun run() {
        dependsOn(buildTask)
        val runDir = project.layout.projectDirectory.dir(runPath.get())
        val modsDir = runDir.dir("mods")
        doFirst {
            modsDir.asFile.mkdirs()
            project.copy {
                from(buildLocation)
                into(modsDir)
            }
        }
        workingDir = runDir.asFile
        classpath = project.files("Server/HytaleServer.jar")
        standardInput = System.`in`
        jvmArgs("-Xmx${xmx.get()}", "-Xms${xms.get()}")
        args("--assets", "Assets.zip", "--disable-sentry")
    }
}
