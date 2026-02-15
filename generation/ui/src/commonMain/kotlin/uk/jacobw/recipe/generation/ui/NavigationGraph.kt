package uk.jacobw.recipe.generation.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.generation.ui.input.InputScreen
import uk.jacobw.recipe.generation.ui.loading.GenerationLoadingScreen

fun NavGraphBuilder.generationGraph(
    navController: NavController,
    onGeneratedRecipeCreated: (String) -> Unit,
) {
    navigation<GenerationRoutes.Root>(
        startDestination = GenerationRoutes.Input,
    ) {
        composable<GenerationRoutes.Input> {
            InputScreen(
                submitPrompt = { prompt ->
                    navController.navigate(GenerationRoutes.Loading(prompt))
                },
            )
        }

        composable<GenerationRoutes.Loading> {
            GenerationLoadingScreen(
                onGenerationComplete = { recipeId ->
                    navController.popBackStack()
                    onGeneratedRecipeCreated(recipeId)
                },
            )
        }
    }
}

sealed class GenerationRoutes : NavigationRoute {
    @Serializable
    data object Root : GenerationRoutes()

    @Serializable
    data object Input : GenerationRoutes()

    @Serializable
    data class Loading(
        val recipePrompt: String,
    ) : GenerationRoutes()
}
