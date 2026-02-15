package uk.jacobw.recipe.generation.ui.loading

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.generation.domain.usecase.GenerateAndStoreRecipeUseCase
import uk.jacobw.recipe.generation.ui.GenerationRoutes

@KoinViewModel
class GenerationLoadingViewModel(
    savedStateHandle: SavedStateHandle,
    private val generateAndStoreRecipe: GenerateAndStoreRecipeUseCase,
) : ViewModel() {
    private val prompt = savedStateHandle.toRoute<GenerationRoutes.Loading>().recipePrompt

    val generatedRecipeId =
        generateAndStoreRecipe(prompt)
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = null,
            )
}
