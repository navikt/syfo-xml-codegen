import java.io.File;

import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*


data class Jaxb2Config (
        val targetPackage: String,
        val schema: String,
        val encoding: String,
        val fork: Boolean = true,
        val outputDir: String? = null,
        val binding: String? = null
) : java.io.Serializable

open class Jaxb2Task : DefaultTask() {

    @OutputDirectory
    lateinit var outputDir: File
    @InputDirectory
    lateinit var sourceDir: File
    @Optional
    @InputDirectory
    var xjbDir: File? = null
    @Input
    lateinit var config: List<Jaxb2Config>

    @TaskAction
    fun generateFiles() {
        outputDir.deleteRecursively()
        outputDir.mkdirs()

        ant.withGroovyBuilder {
            "taskdef"(
                    "name" to "xjc",
                    "classpath" to project.configurations["xjc"].asPath,
                    "classname" to "org.jvnet.jaxb2_commons.xjc.XJC2Task"
            )
            config.forEach {
                "xjc"(
                        "destdir" to (it.outputDir ?: outputDir),
                        "package" to it.targetPackage,
                        "schema" to "$sourceDir/${it.schema}",
                        "fork" to "${it.fork}"
                ) {
                    if (it.binding != null) {
                        "binding"("dir" to xjbDir!!, "includes" to it.binding)
                    }
                }
            }
        }
    }
}

