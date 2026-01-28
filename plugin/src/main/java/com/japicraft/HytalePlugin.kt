package com.japicraft

import org.gradle.api.Plugin
import org.gradle.api.Project

class HytalePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val updateExt = project.extensions.create("updateServer", UpdateTaskExtension::class.java).apply {
            downloaderUrl.convention("https://downloader.hytale.com/hytale-downloader.zip")
            runPath.convention("run")
            downloaderPath.convention("downloader")
            downloaderName.convention("hytale-downloader.zip")
            usedDownloader.convention("hytale-downloader-windows-amd64.exe")
            serverName.convention("server.zip")
        }
        project.tasks.register("updateServer", UpdateTask::class.java) {
            group = "hytale"
            description = "Updates the Hytale development server."
            downloaderUrl.set(updateExt.downloaderUrl)
            runPath.set(updateExt.runPath)
            downloaderPath.set(updateExt.downloaderPath)
            downloaderName.set(updateExt.downloaderName)
            usedDownloader.set(updateExt.usedDownloader)
            serverName.set(updateExt.serverName)
        }
        val runExt = project.extensions.create("runServer", RunTaskExtension::class.java).apply {
            runPath.convention("run")
            buildTask.convention("shadowJar")
            buildLocation.convention("build/libs/StarterHytaleMod.jar")
            xmx.convention("4G")
            xms.convention("4G")
        }
        project.tasks.register("runServer", RunTask::class.java) {
            group = "hytale"
            description = "Runs the Hytale development server."
            runPath.set(runExt.runPath)
            buildTask.set(runExt.buildTask)
            buildLocation.set(runExt.buildLocation)
            xmx.set(runExt.xmx)
            xms.set(runExt.xms)
            classpath = project.files(
                project.layout.projectDirectory.dir(runPath.get()).file("Server/HytaleServer.jar").asFile.absolutePath
            )
        }
    }
}
