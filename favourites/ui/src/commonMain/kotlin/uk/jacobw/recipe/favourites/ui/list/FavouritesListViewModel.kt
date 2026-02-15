package uk.jacobw.recipe.favourites.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.favourites.domain.model.SavedRecipe
import uk.jacobw.recipe.favourites.domain.usecase.ObserveFavouritesUseCase
import uk.jacobw.recipe.favourites.domain.usecase.RemoveFavouriteUseCase

@KoinViewModel
class FavouritesListViewModel(
    observeFavouritesUseCase: ObserveFavouritesUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
) : ViewModel() {
    val favourites =
        observeFavouritesUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun removeFavourite(contentHash: String) {
        viewModelScope.launch {
            removeFavouriteUseCase(contentHash)
        }
    }
}

internal data class FavouritesListUiState(
    val favourites: List<SavedRecipe> = emptyList(),
)
