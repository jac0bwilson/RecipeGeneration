plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("ktlint-convention")
    id("koin-convention")
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "GenerationDomain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":generation:data"))
        }
        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
        }
    }
}

android {
    namespace = "uk.jacobw.recipe.generation.domain"
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
