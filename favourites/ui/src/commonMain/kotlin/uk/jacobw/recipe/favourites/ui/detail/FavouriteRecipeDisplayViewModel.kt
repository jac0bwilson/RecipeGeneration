package uk.jacobw.recipe.favourites.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.favourites.domain.usecase.ObserveFavouriteByHashUseCase
import uk.jacobw.recipe.favourites.domain.usecase.RemoveFavouriteUseCase
import uk.jacobw.recipe.favourites.ui.FavouritesRoutes

@KoinViewModel
class FavouriteRecipeDisplayViewModel(
    savedStateHandle: SavedStateHandle,
    observeFavouriteByHashUseCase: ObserveFavouriteByHashUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
) : ViewModel() {
    private val contentHash = savedStateHandle.toRoute<FavouritesRoutes.Detail>().contentHash

    val savedRecipe =
        observeFavouriteByHashUseCase(contentHash).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun removeFavourite() {
        viewModelScope.launch {
            removeFavouriteUseCase(contentHash)
        }
    }
}
