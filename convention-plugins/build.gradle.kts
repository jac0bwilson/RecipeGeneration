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
    }
}

dependencies {
    implementation(libs.ktlint.gradle)
    implementation(libs.compose.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
