tasks {
    val generateJaxb2 by creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.legeerklaering",
                        schema = "NAV-Legeerklaring-2008-06-06.xsd",
                        encoding = "UTF-8"
                )
        )
    }

    withType<JavaCompile> {
        dependsOn(generateJaxb2)
        options.encoding = "UTF-8"
    }
}
