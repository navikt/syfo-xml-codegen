tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.sm2022",
                        schema = "HelseOpplysningerArbeidsuforhet_2022-05-03.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                )
        )
    }

    withType<JavaCompile> {
        dependsOn(generateJaxb2)
    }
}
