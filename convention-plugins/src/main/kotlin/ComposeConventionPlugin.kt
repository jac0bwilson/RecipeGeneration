import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.getByType<KotlinMultiplatformExtension>().sourceSets.getByName("commonMain").dependencies {
                implementation(libs.findLibrary("compose.runtime").get())
                implementation(libs.findLibrary("compose.foundation").get())
                implementation(libs.findLibrary("compose.material3").get())
                implementation(libs.findLibrary("compose.ui").get())
                implementation(libs.findLibrary("compose.components.resources").get())
                implementation(libs.findLibrary("compose.ui.tooling.preview").get())
                implementation(libs.findLibrary("compose.navigation").get())

                implementation(libs.findLibrary("androidx.lifecycle.viewmodelCompose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
            }
        }
    }
}
