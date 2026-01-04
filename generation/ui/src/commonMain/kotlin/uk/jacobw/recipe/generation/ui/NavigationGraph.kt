package uk.jacobw.recipe.generation.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.generation.ui.input.InputScreen

fun NavGraphBuilder.generationGraph() {
    composable<GenerationRoutes.Input> {
        InputScreen()
    }
}

sealed class GenerationRoutes : NavigationRoute {
    @Serializable data object Input : GenerationRoutes()
}
