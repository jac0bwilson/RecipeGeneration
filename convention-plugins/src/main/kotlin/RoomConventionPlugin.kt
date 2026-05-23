import androidx.room.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("ksp").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("room").get().get().pluginId)

            val kotlinMultiplatform = extensions.getByType<KotlinMultiplatformExtension>()

            kotlinMultiplatform.sourceSets.getByName("commonMain").dependencies {
                implementation(libs.findLibrary("androidx.room.runtime").get())
                implementation(libs.findLibrary("androidx.sqlite.bundled").get())
            }

            kotlinMultiplatform.sourceSets.findByName("androidMain")?.dependencies {
                implementation(libs.findLibrary("androidx.room.sqlite.wrapper").get())
            }

            extensions.configure(RoomExtension::class.java) {
                schemaDirectory("$projectDir/schemas")
            }
        }
    }
}
