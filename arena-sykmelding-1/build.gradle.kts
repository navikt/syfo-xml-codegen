tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.arena.sykemelding",
                        schema = "ArenaSykmelding_1.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                ),
                Jaxb2Config(
                        targetPackage = "no.nav.helse.eiaDokumentInfo",
                        schema = "EiaDokumentInfo1-0.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                )
        )
    }

    withType<JavaCompile> {
        dependsOn(generateJaxb2)
    }
}
