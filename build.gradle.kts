/*
 * The following code is created by shrralis.
 *
 *  - build.gradle.kts (Last change: 2/25/23, 11:29 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.8.10"
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
