package com.example.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

class TestTransform : Transform() {
    private val TAG = "TestTransform"
    override fun getName(): String {
        log("getName() called")
        return "TestTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        log("getInputTypes() called")
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        log("getScopes() called")
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        log("isIncremental() called")
        return false
    }

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        log("transform() called with: transformInvocation = $transformInvocation")
        log("context = ${transformInvocation.context}")
        log("inputs = ${transformInvocation.inputs}")
        log("isIncremental = ${transformInvocation.isIncremental}")
        log("outputProvider = ${transformInvocation.outputProvider}")
        log("referencedInputs = ${transformInvocation.referencedInputs}")
        log("secondaryInputs = ${transformInvocation.secondaryInputs}")
        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach { directoryInput ->
                val path = directoryInput.file.absolutePath
                log("Begin to inject: $path")
                val dest = transformInvocation.outputProvider.getContentLocation(
                    directoryInput.name,
                    directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                )
                log("Directory output dest: ${dest.absolutePath}")
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.forEach { jarInput ->
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jarInput.name,
                    jarInput.contentTypes,
                    jarInput.scopes,
                    Format.JAR
                )
                println("Jar name: ${jarInput.name} output dest: ${dest.absolutePath}")
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

    }

    private fun log(msg: String) {
        println("$TAG: $msg")
    }
}