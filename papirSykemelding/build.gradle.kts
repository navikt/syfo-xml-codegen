version = "1.0.0-SNAPSHOT"
tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.sykSkanningMeta",
                        schema = "syk_skanning_meta.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}
