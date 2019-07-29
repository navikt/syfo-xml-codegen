import java.util.Date
import org.gradle.api.tasks.bundling.Jar
import de.marcphilipp.gradle.nexus.NexusPublishExtension

plugins {
    java
    `maven-publish`
    signing
    id("io.codearte.nexus-staging") version "0.21.0"
    id("de.marcphilipp.nexus-publish") version "0.2.0" apply false
}

allprojects {
    group = "no.nav.helse.xml"
    version = "1.0.22"

    repositories {
        mavenCentral()
        jcenter()
    }

}


nexusStaging {
    packageGroup = "no.nav.helse.xml"
    username = System.getenv("SONATYPE_USERNAME")
    password = System.getenv("SONATYPE_PASSWORD")
}

subprojects {
    group = "no.nav"
    version = "1.0.3"

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "de.marcphilipp.nexus-publish")

    configure<NexusPublishExtension> {
        username.set(System.getenv("SONATYPE_USERNAME"))
        password.set(System.getenv("SONATYPE_PASSWORD"))
    }

    val jaxbBasicAntVersion = "1.11.1"
    val jaxbVersion = "2.3.0.1"
    val jaxbApiVersion = "2.1"
    val javaTimeAdapterVersion = "1.1.3"

    val xjc by configurations.creating

    dependencies {
        xjc("javax.xml.bind", "jaxb-api", jaxbApiVersion)
        xjc("com.sun.xml.bind", "jaxb-xjc", jaxbVersion)
        xjc("com.sun.xml.bind", "jaxb-impl", jaxbVersion)
        xjc("com.sun.xml.bind", "jaxb-core", jaxbVersion)
        xjc("org.jvnet.jaxb2_commons", "jaxb2-basics-ant", jaxbBasicAntVersion)
        xjc("com.migesok", "jaxb-java-time-adapters", javaTimeAdapterVersion)
        implementation("javax.xml.bind:jaxb-api:2.3.0")
        implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")
        implementation("com.migesok", "jaxb-java-time-adapters", javaTimeAdapterVersion)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    ext["xjbOutputDir"] = file("$buildDir/generated-sources/jaxb2")

    sourceSets["main"].java {
        srcDirs(ext["xjbOutputDir"] as File)
    }

    tasks.register<Jar>("sourcesJar") {
        from(sourceSets.main.get().allJava)
        archiveClassifier.set("sources")
    }

    tasks.register<Jar>("javadocJar") {
        from(tasks.javadoc)
        archiveClassifier.set("javadoc")
    }

    publishing {
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/navikt")
                this.credentials {
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifact(tasks.getByName("sourcesJar"))
                artifact(tasks.getByName("javadocJar"))
                pom {
                    name.set("SYFO XML beans")
                    description.set("A collection of XML beans")
                    url.set("https://github.com/navikt/syfo-xml-codegen")

                    organization {
                        name.set("NAV (Arbeids- og velferdsdirektoratet) - The Norwegian Labour and Welfare Administration")
                        url.set("https://www.nav.no/")
                    }

                    developers {
                        developer {
                            organization.set("NAV (Arbeids- og velferdsdirektoratet) - The Norwegian Labour and Welfare Administration")
                            organizationUrl.set("https://www.nav.no/")
                        }
                    }

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/navikt/syfo-xml-codegen.git")
                        developerConnection.set("scm:git:https://github.com/navikt/syfo-xml-codegen.git")
                        url.set("https://github.com/navikt/syfo-xml-codegen")
                    }
                }
            }
        }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications["mavenJava"])
    }

    tasks {
        register("printVersion") {
            doLast {
                println(project.version)
            }
        }

        val generatePomPropertiesForJar by registering {
            val outputDir = file("$buildDir/pom-properties")
            outputDir.mkdirs()
            val outputFile = file("$outputDir/pom.properties")
            outputFile.writeText("""
#Generated by Gradle
#${Date()}
groupId=${project.group}
artifactId=${project.name}
version=${project.version}
""".trimIndent())
            outputs.file(outputFile)
        }

        withType<Jar> {
            val generatePomFileForMavenJavaPublication = getByName("generatePomFileForMavenJavaPublication")
            dependsOn(generatePomPropertiesForJar, generatePomFileForMavenJavaPublication)

            into("META-INF/maven/${project.group}/${project.name}") {
                from(generatePomPropertiesForJar)
                from(generatePomFileForMavenJavaPublication) {
                    rename(".+", "pom.xml")
                }
            }
        }
    }
}
