tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.arenaSykemelding",
                        schema = "ArenaSykmelding_1.xsd",
                        encoding = "UTF-8"
                ),
                Jaxb2Config(
                        targetPackage = "no.nav.helse.eiaDokumentInfo",
                        schema = "EiaDokumentInfo1-0.xsd",
                        encoding = "UTF-8"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}
