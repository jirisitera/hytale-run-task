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
        buildTask.finalizeValueOnRead()
        buildTask.disallowChanges()
        project.afterEvaluate {
            buildTask.orNull?.let {
                dependsOn(it)
            }
        }
    }
    override fun exec() {
        val runDir = project.layout.projectDirectory.dir(runPath.get())
        val modsDir = runDir.dir("Server/mods")
        if (!modsDir.asFile.exists()) {
            modsDir.asFile.mkdirs()
        }
        logger.lifecycle("[Launcher] Copying custom mod...")
        project.copy {
            from(buildLocation.get())
            into(modsDir)
        }
        logger.lifecycle("[Launcher] Booting up server...")
        jvmArgs("-Xmx${xmx.get()}", "-Xms${xms.get()}")
        args("--assets", runPath.get() + "/Assets.zip", "--disable-sentry")
        super.exec()
    }
}
