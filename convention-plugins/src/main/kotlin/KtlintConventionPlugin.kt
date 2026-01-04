import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            extensions.getByType<KtlintExtension>().apply {
                version.set("1.5.0")
                verbose.set(true)
                outputToConsole.set(true)
                coloredOutput.set(true)

                filter {
                    exclude { element -> element.file.path.contains("resourceGenerator") }
                    exclude { element -> element.file.path.contains("/generated/") }
                    exclude { element -> element.file.path.contains("buildkonfig") }
                }
            }

            dependencies {
                add("ktlintRuleset", libs.findLibrary("ktlint.ruleset.compose").get())
            }

            val kspTasks = tasks.matching { it.name.startsWith("ksp") }
            tasks.matching { it.name.contains("Ktlint") }.configureEach {
                dependsOn(kspTasks)
            }
        }
    }
}
