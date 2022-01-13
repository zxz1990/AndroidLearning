package com.example.transform

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("TransformPlugin apply() called with: target = $target")
        var findByType = target.extensions.findByType(AppExtension::class.java)
        findByType!!.registerTransform(TestTransform())
    }
}