package uk.jacobw.recipe.recipedisplay.ui.generated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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

@OptIn(ExperimentalCoroutinesApi::class)
@KoinViewModel
class RecipeDisplayViewModel(
    private val observeRecipe: ObserveRecipeUseCase,
    private val observeIsFavourite: ObserveIsFavouriteUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
) : ViewModel() {
    private val reference = MutableStateFlow<RecipeReference?>(null)

    val recipe =
        reference
            .filterNotNull()
            .flatMapLatest { recipeReference ->
                observeRecipe(recipeReference)
            }.stateIn(
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

    fun setReference(recipeReference: RecipeReference) {
        reference.value = recipeReference
    }

    fun onFavouriteClicked() {
        val currentRecipe = recipe.value ?: return

        viewModelScope.launch {
            toggleFavourite(currentRecipe)
        }
    }
}
