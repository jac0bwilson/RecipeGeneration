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

            val compose = extensions.getByType<ComposeExtension>().dependencies
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.getByType<KotlinMultiplatformExtension>().sourceSets.getByName("commonMain").dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.findLibrary("compose.navigation").get())
            }

            dependencies {
                add("debugImplementation", compose.uiTooling)
            }
        }
    }
}
