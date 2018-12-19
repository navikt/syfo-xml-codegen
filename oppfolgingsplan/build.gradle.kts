tasks {
    val generateJaxb2 by tasks.creating(Jaxb2Task::class) {
        outputDir = ext["xjbOutputDir"] as File
        sourceDir = file("$projectDir/src/main/xsd")
        config = listOf(
                Jaxb2Config(
                        targetPackage = "no.nav.helse.op2012",
                        schema = "Oppfolgingsplan_Altinn.xsd",
                        encoding = "UTF-8"
                ),
                Jaxb2Config(
                        targetPackage = "no.nav.helse.op2014",
                        schema = "Oppfolgingsplan2_4M.xsd",
                        encoding = "UTF-8"
                ),
                Jaxb2Config(
                        targetPackage = "no.nav.helse.op2016",
                        schema = "Oppfolgingsplan201607_2.xsd",
                        encoding = "UTF-8"
                )
        )
    }

    "compileJava" {
        dependsOn(generateJaxb2)
    }
}
