# Hytale Run Task

A Gradle task for automatically setting up a development Hytale server. Useful for rapid Hytale mod development.

Both Hytale-related tasks can be found in the `hytale` folder (aka. group) in the Gradle task list. Here's what each one does.

## Setup task - updateServer

> [!NOTE]
> You must use this task on initial setup, but after that, how often you run it is up to you.
> 
> With that said, you should run this task occasionally to make sure your server is up-to-date with your Hytale launcher, in order to actually join the server.

This task will first automatically download and run the Hytale Downloader tool.

> [!IMPORTANT]
> You must authenticate the Downloader tool's session with your Hytale account.
> Simply click the link that appears in the console when the task is running.
> If you don't do this, the task won't be able to download anything and get stuck waiting.

Once authenticated, the tool will download the Hytale server JAR and other required assets.

And you're ready! Now it's time to boot the server up! For that, you can use the next task.

## Run task - runServer

> [!IMPORTANT]
> You must run the setup task (updateServer) before running this task!

This task automatically builds your mod with Gradle and ShadowJar, then moves the finished mod the the `mods` folder in the server's folder (`run`);

Then, it spins up a Hytale server! You can join it at the `localhost` address. (runs on the default port)

## Example Usage

You can use this example however you want. It shows a basic setup of how to use these tasks and more importantly, how to customize them.

Notice the setters in the `updateServer` and `runServer` tasks below. You can customize everything from the run folder to the name of the downloader.

Note that the values listed below are the defaults for the tasks, so if you don't need to change them, you can simply not have them in your `gradle.build.kts` at all.

```kotlin
plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
    id("com.japicraft.hytale") version "2.1"
}
group = "your.group"
version = "1.0"
repositories {
    mavenCentral()
}
dependencies {
    compileOnly(files("run/Server/HytaleServer.jar"))
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}
tasks {
    shadowJar {
        archiveBaseName.set("StarterHytaleMod")
        archiveVersion.set("")
        archiveClassifier.set("")
    }
    updateServer {
        downloaderUrl.set("https://downloader.hytale.com/hytale-downloader.zip")
        runPath.set("run")
        downloaderPath.set("downloader")
        downloaderName.set("hytale-downloader.zip")
        usedDownloader.set("hytale-downloader-windows-amd64.exe")
        serverName.set("server.zip")
    }
    runServer {
        runPath.set("run")
        buildTask.set("shadowJar")
        buildLocation.set("build/libs/StarterHytaleMod.jar")
        xmx.set("4G")
        xms.set("4G")
    }
}
```
