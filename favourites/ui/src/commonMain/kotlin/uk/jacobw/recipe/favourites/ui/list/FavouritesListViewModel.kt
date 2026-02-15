package uk.jacobw.recipe.favourites.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.favourites.domain.usecase.ObserveFavouritesUseCase
import uk.jacobw.recipe.favourites.domain.usecase.RemoveFavouriteUseCase

@KoinViewModel
class FavouritesListViewModel(
    private val observeFavourites: ObserveFavouritesUseCase,
    private val removeFavourite: RemoveFavouriteUseCase,
) : ViewModel() {
    val favourites =
        observeFavourites()
            .map { savedRecipes ->
                savedRecipes.map { savedRecipe ->
                    FavouriteListItem(
                        contentHash = savedRecipe.contentHash,
                        title = savedRecipe.recipe.title,
                        servings = savedRecipe.recipe.servings,
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    fun removeFavouriteRecipe(contentHash: String) {
        viewModelScope.launch {
            removeFavourite(contentHash)
        }
    }
}
