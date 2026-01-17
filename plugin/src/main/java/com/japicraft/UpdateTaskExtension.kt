package com.japicraft

import org.gradle.api.provider.Property

abstract class UpdateTaskExtension {
    abstract val downloaderUrl: Property<String>
    abstract val runPath: Property<String>
    abstract val downloaderPath: Property<String>
    abstract val downloaderName: Property<String>
    abstract val usedDownloader: Property<String>
    abstract val serverName: Property<String>
}
