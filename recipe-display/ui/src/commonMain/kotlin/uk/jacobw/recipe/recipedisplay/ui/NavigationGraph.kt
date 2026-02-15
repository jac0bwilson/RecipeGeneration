package uk.jacobw.recipe.recipedisplay.ui

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.recipedisplay.domain.model.RecipeReference
import uk.jacobw.recipe.recipedisplay.ui.generated.RecipeDisplayScreen

fun NavGraphBuilder.recipeDisplayGraph(
    navController: NavController,
) {
    navigation<RecipeDisplayRoutes.Root>(
        startDestination = RecipeDisplayRoutes.Entry,
    ) {
        composable<RecipeDisplayRoutes.Entry> {
            // Placeholder destination used only as a nested graph start destination.
        }

        composable<RecipeDisplayRoutes.Generated> { backStackEntry ->
            val route = backStackEntry.toRoute<RecipeDisplayRoutes.Generated>()

            RecipeDisplayScreen(
                recipeReference =
                    remember(route.recipeId) {
                        RecipeReference.Generated(route.recipeId)
                    },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }

        composable<RecipeDisplayRoutes.Favourite> { backStackEntry ->
            val route = backStackEntry.toRoute<RecipeDisplayRoutes.Favourite>()

            RecipeDisplayScreen(
                recipeReference =
                    remember(route.contentHash) {
                        RecipeReference.Favourite(route.contentHash)
                    },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}

sealed class RecipeDisplayRoutes : NavigationRoute {
    @Serializable
    data object Root : RecipeDisplayRoutes()

    @Serializable
    data object Entry : RecipeDisplayRoutes()

    @Serializable
    data class Generated(
        val recipeId: String,
    ) : RecipeDisplayRoutes()

    @Serializable
    data class Favourite(
        val contentHash: String,
    ) : RecipeDisplayRoutes()
}
