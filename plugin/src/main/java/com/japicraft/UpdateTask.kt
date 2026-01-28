package com.japicraft

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class UpdateTask : Exec() {
    @get:Input
    abstract val downloaderUrl: Property<String>
    @get:Input
    abstract val runPath: Property<String>
    @get:Input
    abstract val downloaderPath: Property<String>
    @get:Input
    abstract val downloaderName: Property<String>
    @get:Input
    abstract val usedDownloader: Property<String>
    @get:Input
    abstract val serverName: Property<String>
    @TaskAction
    fun run() {
        val runDir = project.layout.projectDirectory.dir(runPath.get())
        val downloaderDir = runDir.dir(downloaderPath.get())
        val downloaderZip = downloaderDir.file(downloaderName.get())
        val serverZip = downloaderDir.file(serverName.get())
        if (!downloaderDir.asFile.exists()) {
            downloaderDir.asFile.mkdirs()
        }
        logger.lifecycle("[UPDATER] Fetching Hytale Downloader tool...")
        downloaderZip.asFile.writeBytes(project.uri(downloaderUrl.get()).toURL().readBytes())
        logger.lifecycle("[UPDATER] Unpacking Hytale Downloader tool...")
        project.copy {
            from(project.zipTree(downloaderZip))
            into(downloaderDir)
        }
        logger.lifecycle("[UPDATER] Downloading Hytale server...")
        commandLine(downloaderDir.file(usedDownloader.get()), "-download-path", serverZip)
        exec()
        logger.lifecycle("[UPDATER] Unpacking Hytale server...")
        project.copy {
            from(project.zipTree(serverZip))
            into(runDir)
        }
        logger.lifecycle("[UPDATER] Hytale server updated successfully!")
    }
}
