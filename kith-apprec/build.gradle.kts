tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        System.setProperty("javax.xml.accessExternalSchema", "all")
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.apprecV10",
                        schema = "AppRec-v1.0.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                ),
                Jaxb2Config(
                        targetPackage = "no.nav.helse.apprecV11",
                        schema = "AppRec-v1.1.xsd",
                        encoding = "UTF-8",
                        binding = "binding.xml"
                )
        )
    }
    withType<JavaCompile> {
        dependsOn(generateJaxb2)
    }
}
