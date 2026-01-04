import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.getByType<KotlinMultiplatformExtension>().sourceSets.getByName("commonMain").dependencies {
                implementation(libs.findLibrary("koin.annotations").get())
                implementation(dependencies.platform(libs.findLibrary("koin.bom").get()))
                implementation(libs.findLibrary("koin.compose").get())
                implementation(libs.findLibrary("koin.compose.viewmodel").get())
            }

            val kmp = extensions.getByType<KotlinMultiplatformExtension>()
            val commonMain = kmp.sourceSets.getByName("commonMain")
            commonMain.kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            val koinKspCompiler = libs.findLibrary("koin.ksp.compiler").get()

            afterEvaluate {
                tasks.configureEach {
                    val name = name
                    if (name.startsWith("ksp") && !name.contains("Metadata") && name.contains("Kotlin")) {
                        dependsOn("kspCommonMainKotlinMetadata")
                    }
                }

                configurations.matching { it.name.startsWith("ksp") }.forEach { config ->
                    dependencies.add(config.name, koinKspCompiler)
                }
            }
        }
    }
}