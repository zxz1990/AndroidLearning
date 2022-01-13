package com.example.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("MyPlugin apply() called with: target = [$target]")
    }
}