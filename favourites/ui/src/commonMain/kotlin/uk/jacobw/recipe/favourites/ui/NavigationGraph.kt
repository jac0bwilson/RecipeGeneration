package uk.jacobw.recipe.favourites.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.favourites.ui.list.FavouritesListScreen

fun NavGraphBuilder.favouritesGraph(
    navController: NavController,
    favouriteDetailScreen: @Composable (onBackClick: () -> Unit) -> Unit,
) {
    navigation<FavouritesRoutes.Root>(
        startDestination = FavouritesRoutes.List,
    ) {
        composable<FavouritesRoutes.List> {
            FavouritesListScreen(
                onRecipeSelected = { contentHash ->
                    navController.navigate(FavouritesRoutes.Detail(contentHash))
                },
            )
        }

        composable<FavouritesRoutes.Detail> {
            favouriteDetailScreen {
                navController.popBackStack()
            }
        }
    }
}

sealed class FavouritesRoutes : NavigationRoute {
    @Serializable
    data object Root : FavouritesRoutes()

    @Serializable
    data object List : FavouritesRoutes()

    @Serializable
    data class Detail(
        val contentHash: String,
    ) : FavouritesRoutes()
}
