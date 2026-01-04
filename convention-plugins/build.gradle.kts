plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("ktlint-convention") {
            id = "ktlint-convention"
            implementationClass = "KtlintConventionPlugin"
        }
    }
}

dependencies {
    implementation(libs.ktlint.gradle)
}
