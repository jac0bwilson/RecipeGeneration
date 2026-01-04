plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("compose-convention")
    id("ktlint-convention")
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreUI"
            isStatic = true
        }
    }
}

android {
    namespace = "uk.jacobw.recipe.core.ui"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()
    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }
}
