package uk.jacobw.recipe.favourites.ui.list

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouritesListScreen(
    onSelectRecipe: (String) -> Unit,
    viewModel: FavouritesListViewModel = koinViewModel(),
) {
    val favourites = viewModel.favourites.collectAsStateWithLifecycle().value

    FavouritesListLayout(
        favourites = favourites,
        onRecipeSelect = onSelectRecipe,
        onRemoveFavourite = viewModel::removeFavouriteRecipe,
    )
}
