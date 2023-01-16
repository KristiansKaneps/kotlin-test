plugins {
    kotlin("js")
}

val kotlinxHtmlVersion = project.property("kotlinx.html.version") as String
val kotlinxSerializationVersion = project.property("kotlinx.serialization.version") as String
val kotlinxCoroutinesVersion = project.property("kotlinx.coroutines.version") as String
val kotlinWrappersSuffix = project.property("kotlin.wrappers.suffix") as String

kotlin {
    js(IR) {
        moduleName = "app"
        useCommonJs()
        nodejs()
        browser {
            compilations.all {
                kotlinOptions {
                    metaInfo = true
                    sourceMap = true
                    sourceMapEmbedSources = "always"
                    moduleKind = "commonjs"
                    main = "call"
                }
            }
		    commonWebpackConfig {
		        cssSupport {
                    enabled.set(true)
				}
		    }
        }
        binaries.executable()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")

    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-js:$kotlinxSerializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kotlinxCoroutinesVersion")

    implementation(project(":common"))

    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-$kotlinWrappersSuffix")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-$kotlinWrappersSuffix")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.4-$kotlinWrappersSuffix")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-$kotlinWrappersSuffix")

    testImplementation("org.jetbrains.kotlin:kotlin-test-js")

    implementation(npm("react-tooltip", "5.5.1"))
}

val browserDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(browserDist.name, tasks.named("browserDistribution").map { it.outputs.files.files.single() })
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().download = true
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().download = true
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().lockFileDirectory =
        project.rootDir.resolve("kotlin-js-store")
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().lockFileName = "yarn.lock"
}
