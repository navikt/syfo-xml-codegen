tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        System.setProperty("javax.xml.accessExternalSchema", "all")
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main")
        xjbDir = file("$projectDir/src/main/xjb")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.xmlMsgHead",
                        schema = "xsd/MsgHead-v1_2.xsd",
                        encoding = "ISO-8859-1",
                        binding = "binding.xml"
                )
        )
    }

    withType<JavaCompile> {
        dependsOn(generateJaxb2)
    }
}
