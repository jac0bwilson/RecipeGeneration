package uk.jacobw.recipe.generation.ui.display

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel
import uk.jacobw.recipe.generation.domain.usecase.GenerateRecipeUseCase
import uk.jacobw.recipe.generation.ui.GenerationRoutes

@KoinViewModel
class RecipeDisplayViewModel(
    savedStateHandle: SavedStateHandle,
    private val generateRecipeUseCase: GenerateRecipeUseCase,
) : ViewModel() {
    private val prompt = savedStateHandle.toRoute<GenerationRoutes.RecipeDisplay>().recipePrompt

    val recipe =
        generateRecipeUseCase(prompt).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
}
