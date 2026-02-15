package uk.jacobw.recipe.recipedisplay.ui.display

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.recipedisplay.domain.model.RecipeReference
import uk.jacobw.recipe.recipedisplay.domain.usecase.ObserveIsFavouriteUseCase
import uk.jacobw.recipe.recipedisplay.domain.usecase.ObserveRecipeUseCase
import uk.jacobw.recipe.recipedisplay.domain.usecase.ToggleFavouriteUseCase
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplayRoutes
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplaySource

@OptIn(ExperimentalCoroutinesApi::class)
@KoinViewModel
class RecipeDisplayViewModel(
    savedStateHandle: SavedStateHandle,
    private val observeRecipe: ObserveRecipeUseCase,
    private val observeIsFavourite: ObserveIsFavouriteUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
) : ViewModel() {
    private val reference =
        savedStateHandle
            .toRoute<RecipeDisplayRoutes.Recipe>()
            .toDomainReference()

    val closesAfterFavouriteToggle = reference is RecipeReference.Favourite

    val recipe =
        observeRecipe(reference).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    val isFavourite =
        recipe
            .filterNotNull()
            .flatMapLatest { generatedRecipe ->
                observeIsFavourite(generatedRecipe)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    fun onFavouriteClicked() {
        val currentRecipe = recipe.value ?: return

        viewModelScope.launch {
            toggleFavourite(currentRecipe)
        }
    }

    private fun RecipeDisplayRoutes.Recipe.toDomainReference(): RecipeReference =
        when (RecipeDisplaySource.fromNavValue(source)) {
            RecipeDisplaySource.GENERATED -> RecipeReference.Generated(reference)
            RecipeDisplaySource.FAVOURITE -> RecipeReference.Favourite(reference)
        }
}
