tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
            Jaxb2Config(
                targetPackage = "no.nav.helse.base64container",
                schema = "kith-base64.xsd",
                encoding = "UTF-8"
            )
        )
    }

    withType<JavaCompile> {
        dependsOn(generateJaxb2)
    }
}