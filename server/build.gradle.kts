plugins {
    id("java")
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.0"
}

val micronautVersion = project.property("micronaut.version") as String
val kotlinVersion = project.property("kotlin.version") as String
val kotlinxHtmlVersion = project.property("kotlinx.html.version") as String
val kotlinxSerializationVersion = project.property("kotlinx.serialization.version") as String
val kotlinWrappersSuffix = project.property("kotlin.wrappers.suffix") as String

val browserDist: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    implementation(project(":common"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    browserDist(
        project(
            mapOf(
                "path" to ":app",
                "configuration" to "browserDist"
            )
        )
    )

    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.ktor:ktor-jackson")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.kotlin:micronaut-ktor")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("api.Application")
}
java {
    sourceCompatibility = JavaVersion.VERSION_18
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "18"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "18"
        }
    }
}

tasks.withType<Copy>().named("processResources") {
    from(browserDist)
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    version(micronautVersion)
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("api.*")
    }
}
