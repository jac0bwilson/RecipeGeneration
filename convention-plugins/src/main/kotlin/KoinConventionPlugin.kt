import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("koin.compiler").get().get().pluginId)

            extensions.getByType<KotlinMultiplatformExtension>().sourceSets.getByName("commonMain").dependencies {
                implementation(libs.findLibrary("koin.annotations").get())
                implementation(dependencies.platform(libs.findLibrary("koin.bom").get()))
                implementation(libs.findLibrary("koin.compose").get())
                implementation(libs.findLibrary("koin.compose.viewmodel").get())
            }
        }
    }
}
