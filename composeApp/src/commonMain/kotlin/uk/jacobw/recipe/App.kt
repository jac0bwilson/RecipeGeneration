package uk.jacobw.recipe

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.generation.ui.GenerationRoutes
import uk.jacobw.recipe.generation.ui.generationGraph
import uk.jacobw.recipe.generation.ui.getGenerationUiModules

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(
        config =
            KoinConfiguration {
                modules(AppModule().module + getGenerationUiModules())
            },
    ) {
        AppTheme {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = GenerationRoutes.Input,
            ) {
                generationGraph()
            }
        }
    }
}
