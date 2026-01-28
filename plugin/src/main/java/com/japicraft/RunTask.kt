package com.japicraft

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec

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
    init {
        val runDir = project.layout.projectDirectory.dir(runPath.get())
        classpath = project.files(runDir.file("Server/HytaleServer.jar").asFile.absolutePath)
        mainClass.set("com.hypixel.hytale.Main")
        doFirst {
            val modsDir = runDir.dir("mods")
            if (!modsDir.asFile.exists()) {
                modsDir.asFile.mkdirs()
            }
            project.copy {
                from(project.file(buildLocation.get()))
                into(modsDir.asFile)
            }
            setWorkingDir(runDir.asFile)
            jvmArgs("-Xmx${xmx.get()}", "-Xms${xms.get()}")
            args("--assets", "Assets.zip", "--disable-sentry")
        }
    }
    override fun getDependsOn(): MutableSet<Any> {
        super.getDependsOn().add(buildTask.get())
        return super.getDependsOn()
    }
}
