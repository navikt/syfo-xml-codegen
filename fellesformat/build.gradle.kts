version = "1.2-SNAPSHOT"

tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.fellesformat",
                        schema = "NAV_Fellesformat-v1_eia2.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}

