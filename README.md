[![Build status](https://github.com/navikt/syfo-xml-codegen/workflows/Publish%20artifacts/badge.svg)](https://github.com/navikt/syfo-xml-codegen/workflows/Publish%20artifacts/badge.svg)

# SYFO XML Codegen
Since multiple projects reuse the same xml files we have this gradle based project for generating java sources from xsd
and publishing them to our internal nexus server. This project by itself doesn't do much. 

Check GitHub releases to find the latest release version Check GitHub releases
to find the latest release version


## Technologies used

* Kotlin
* Gradle


#### Requirements

* JDK 11

## Build

``` bash 
./gradlew build
 ```


### Upgrading the gradle wrapper

Find the newest version of gradle here: https://gradle.org/releases/ Then run this command:

``` bash
./gradlew wrapper --gradle-version $gradleVersjon
```

### Contact

This project is maintained by [navikt/teamsykmelding](CODEOWNERS)

Questions and/or feature requests?
Please create an [issue](https://github.com/navikt/syfo-xml-codegen/issues)

If you work in [@navikt](https://github.com/navikt) you can reach us at the Slack
channel [#team-sykmelding](https://nav-it.slack.com/archives/CMA3XV997)