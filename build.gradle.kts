import java.net.URI
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.bundling.Jar

plugins {
    java
    `maven-publish`
}

group = "no.nav.helse.xml"
version = "1.0-SNAPSHOT"

subprojects {
    group = "no.nav.helse.xml"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        jcenter()
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
        implementation("com.migesok", "jaxb-java-time-adapters", javaTimeAdapterVersion)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    ext["xjbOutputDir"] = file("$buildDir/generated-sources/jaxb2")

    val sourcesJar by tasks.registering(Jar::class) {
        classifier = "sources"
        from(sourceSets["main"].allSource)
    }


    sourceSets["main"].java {
        srcDirs(ext["xjbOutputDir"] as File)
    }


    publishing {
        repositories {
            maven {
                val buildType = if (project.version.toString().endsWith("-SNAPSHOT")) {
                    "snapshots"
                } else {
                    "releases"
                }
                url = uri("https://repo.adeo.no/repository/maven-$buildType")
            }
        }
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])
                artifact(sourcesJar.get())
            }
        }
    }
}

tasks {
    register("printVersion") {
        doLast {
            println(project.version)
        }
    }
}
