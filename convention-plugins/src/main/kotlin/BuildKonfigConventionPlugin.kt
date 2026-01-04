import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

class BuildKonfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.codingfeline.buildkonfig")

            val localProperties = Properties()
            val localPropertiesFile = rootDir.resolve("local.properties")

            if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
                localPropertiesFile.inputStream().use { input ->
                    localProperties.load(input)
                }
            }

            extensions.getByType<BuildKonfigExtension>().apply {
                packageName = "uk.jacobw.recipe"

                defaultConfigs {
                    val geminiKey: String = localProperties.getProperty("GEMINI_API_KEY")

                    require(geminiKey.isNotEmpty()) {
                        "Register an API key in local.properties"
                    }

                    buildConfigField(STRING, "GEMINI_API_KEY", geminiKey)
                }
            }
        }
    }
}