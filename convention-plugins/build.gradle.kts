plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("ktlint-convention") {
            id = "ktlint-convention"
            implementationClass = "KtlintConventionPlugin"
        }
        register("compose-convention") {
            id = "compose-convention"
            implementationClass = "ComposeConventionPlugin"
        }
        register("koin-convention") {
            id = "koin-convention"
            implementationClass = "KoinConventionPlugin"
        }
        register("buildKonfig-convention") {
            id = "buildKonfig-convention"
            implementationClass = "BuildKonfigConventionPlugin"
        }
    }
}

dependencies {
    implementation(libs.buildKonfig.compiler)
    implementation(libs.buildKonfig.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
    implementation(libs.ktlint.gradle)
    implementation(libs.kotlin.gradle.plugin)
}
