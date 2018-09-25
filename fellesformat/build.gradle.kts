tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.sm2013",
                        schema = "NAV_Fellesformat-v1_eia2.xsd",
                        encoding = "UTF-8"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}
