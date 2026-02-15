package uk.jacobw.recipe.favourites.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import uk.jacobw.recipe.core.ui.navigation.NavigationRoute
import uk.jacobw.recipe.favourites.ui.list.FavouritesListScreen

fun NavGraphBuilder.favouritesGraph(
    onRecipeSelected: (String) -> Unit,
) {
    navigation<FavouritesRoutes.Root>(
        startDestination = FavouritesRoutes.List,
    ) {
        composable<FavouritesRoutes.List> {
            FavouritesListScreen(
                onRecipeSelected = { contentHash ->
                    onRecipeSelected(contentHash)
                },
            )
        }
    }
}

sealed class FavouritesRoutes : NavigationRoute {
    @Serializable
    data object Root : FavouritesRoutes()

    @Serializable
    data object List : FavouritesRoutes()
}
