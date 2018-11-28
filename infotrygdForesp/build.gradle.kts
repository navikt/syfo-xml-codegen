version = "1.0.0"
tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.infotrygdForesp",
                        schema = "RTV_IT_IFT.v14.xsd",
                        encoding = "UTF-8"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}
