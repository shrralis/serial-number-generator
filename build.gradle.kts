/*
 * The following code is created by shrralis.
 *
 *  - build.gradle.kts (Last change: 2/26/23, 11:23 AM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco
    `maven-publish`
    signing
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "com.shrralis"
version = "1.0.0"
description = "Serial Number Generator by Shrralis"

repositories { mavenCentral() }

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
}

ktlint {
    // See more options: https://github.com/JLLeitschuh/ktlint-gradle#configuration
    ignoreFailures.set(false)
    disabledRules.set(listOf("no-wildcard-imports", "filename"))
}

jacoco { toolVersion = "0.8.8" }

tasks {
    wrapper { gradleVersion = "7.6" }

    compileKotlin { dependsOn("ktlintFormat") }

    test {
        useJUnitPlatform()
        testLogging { events("FAILED", "SKIPPED") }
        reports.html.required.set(false)
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit { minimum = 0.7.toBigDecimal() }

                sourceSets.main.get().output.classesDirs.asFileTree.run { classDirectories.setFrom(this) }
            }
        }
    }

    jacocoTestReport {
        withType<Test>().forEach(::executionData)
        sourceSets.main.get().output.classesDirs.asFileTree.run { classDirectories.setFrom(this) }
        sourceDirectories.setFrom(files(sourceSets.main.get().java.srcDirs))
        sourceDirectories.from.addAll(files(sourceSets.main.get().kotlin.srcDirs))

        reports.xml.required.set(true)
    }

    check { dependsOn("test", "jacocoTestReport", "jacocoTestCoverageVerification") }

    create<TestReport>("testReport") {
        destinationDirectory.set(file("$buildDir/reports/tests"))
        testResults.from(test.get().binaryResultsDirectory)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.majorVersion
            javaParameters = true
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all-compatibility")
        }
    }
}

val dokkaOutputDir = "$buildDir/dokka"
tasks.named<DokkaTask>("dokkaHtml") { outputDirectory.set(file(dokkaOutputDir)) }
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(javadocJar) {
                pom {
                    name.set(rootProject.name)
                    description.set(project.description)
                    url.set("https://shrralis.com")
                    inceptionYear.set("2023")
                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("shrralis")
                            name.set("Yaroslav Zhyravov")
                            email.set("root@shrralis.com")
                        }
                    }
                    scm {
                        connection.set("https://github.com/shrralis/serial-number-generator.git")
                        url.set("https://github.com/shrralis/serial-number-generator")
                    }
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/shrralis/serial-number-generator")
                    }
                }
            }
        }

        repositories {
            maven {
                val sonartypeUsername = System.getenv("SONARTYPE_USERNAME")
                val sonartypePassword = System.getenv("SONARTYPE_PASSWORD")

                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = sonartypeUsername
                    password = sonartypePassword
                }
            }
        }
    }
}

signing { sign(publishing.publications) }
