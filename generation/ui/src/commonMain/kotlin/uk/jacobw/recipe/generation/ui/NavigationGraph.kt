package uk.jacobw.recipe.generation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.generation.ui.input.InputScreen

fun NavGraphBuilder.generationGraph(
    navController: NavController,
    generatedRecipeScreen: @Composable (onBackClick: () -> Unit) -> Unit,
) {
    navigation<GenerationRoutes.Root>(
        startDestination = GenerationRoutes.Input,
    ) {
        composable<GenerationRoutes.Input> {
            InputScreen(
                submitPrompt = { prompt ->
                    navController.navigate(GenerationRoutes.RecipeDisplay(prompt))
                },
            )
        }

        composable<GenerationRoutes.RecipeDisplay> {
            generatedRecipeScreen {
                navController.popBackStack()
            }
        }
    }
}

sealed class GenerationRoutes : NavigationRoute {
    @Serializable
    data object Root : GenerationRoutes()

    @Serializable
    data object Input : GenerationRoutes()

    @Serializable
    data class RecipeDisplay(
        val recipePrompt: String,
    ) : GenerationRoutes()
}
