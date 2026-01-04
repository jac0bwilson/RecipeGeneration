package uk.jacobw.recipe.generation.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.generation.ui.display.RecipeDisplayScreen
import uk.jacobw.recipe.generation.ui.input.InputScreen

fun NavGraphBuilder.generationGraph(navController: NavController) {
    composable<GenerationRoutes.Input> {
        InputScreen(
            submitPrompt = { prompt ->
                navController.navigate(GenerationRoutes.RecipeDisplay(prompt))
            },
        )
    }

    composable<GenerationRoutes.RecipeDisplay> {
        RecipeDisplayScreen(
            onNavigationIconClick = {
                navController.popBackStack()
            },
        )
    }
}

sealed class GenerationRoutes : NavigationRoute {
    @Serializable data object Input : GenerationRoutes()

    @Serializable
    data class RecipeDisplay(
        val recipePrompt: String,
    ) : GenerationRoutes()
}
