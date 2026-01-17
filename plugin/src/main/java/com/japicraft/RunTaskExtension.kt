package com.japicraft

import org.gradle.api.provider.Property

abstract class RunTaskExtension {
    abstract val runPath: Property<String>
    abstract val buildTask: Property<String>
    abstract val buildLocation: Property<String>
    abstract val xmx: Property<String>
    abstract val xms: Property<String>
}
