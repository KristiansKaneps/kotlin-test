
rootProject.name = "template"

pluginManagement {
    resolutionStrategy {
        repositories {
            gradlePluginPortal()
        }
    }
}

include("common")
include("app")
include("server")