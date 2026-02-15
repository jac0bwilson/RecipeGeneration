package uk.jacobw.recipe.recipedisplay.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
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

        composable<RecipeDisplayRoutes.Recipe> {
            RecipeDisplayScreen(
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}

@Serializable
enum class RecipeDisplaySource {
    GENERATED,
    FAVOURITE,
}

sealed class RecipeDisplayRoutes : NavigationRoute {
    @Serializable
    data object Root : RecipeDisplayRoutes()

    @Serializable
    data object Entry : RecipeDisplayRoutes()

    @Serializable
    data class Recipe(
        val source: RecipeDisplaySource,
        val reference: String,
    ) : RecipeDisplayRoutes()
}
