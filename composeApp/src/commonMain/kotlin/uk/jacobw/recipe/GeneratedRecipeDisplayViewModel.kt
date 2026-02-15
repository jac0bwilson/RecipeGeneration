package uk.jacobw.recipe

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
import uk.jacobw.recipe.favourites.domain.model.FavouriteDifficulty
import uk.jacobw.recipe.favourites.domain.model.FavouriteDuration
import uk.jacobw.recipe.favourites.domain.model.FavouriteIngredient
import uk.jacobw.recipe.favourites.domain.model.FavouriteInstruction
import uk.jacobw.recipe.favourites.domain.model.FavouriteRecipe
import uk.jacobw.recipe.favourites.domain.usecase.ObserveIsFavouriteUseCase
import uk.jacobw.recipe.favourites.domain.usecase.ToggleFavouriteUseCase
import uk.jacobw.recipe.generation.domain.model.Difficulty
import uk.jacobw.recipe.generation.domain.model.Duration
import uk.jacobw.recipe.generation.domain.model.Ingredient
import uk.jacobw.recipe.generation.domain.model.Instruction
import uk.jacobw.recipe.generation.domain.model.Recipe
import uk.jacobw.recipe.generation.domain.usecase.GenerateRecipeUseCase
import uk.jacobw.recipe.generation.ui.GenerationRoutes

@OptIn(ExperimentalCoroutinesApi::class)
@KoinViewModel
class GeneratedRecipeDisplayViewModel(
    savedStateHandle: SavedStateHandle,
    private val generateRecipeUseCase: GenerateRecipeUseCase,
    private val observeIsFavouriteUseCase: ObserveIsFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) : ViewModel() {
    private val prompt = savedStateHandle.toRoute<GenerationRoutes.RecipeDisplay>().recipePrompt

    val recipe =
        generateRecipeUseCase(prompt).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    val isFavourite =
        recipe
            .filterNotNull()
            .flatMapLatest { generatedRecipe ->
                observeIsFavouriteUseCase(generatedRecipe.toFavouriteRecipe())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    fun onFavouriteClicked() {
        val currentRecipe = recipe.value ?: return

        viewModelScope.launch {
            toggleFavouriteUseCase(currentRecipe.toFavouriteRecipe())
        }
    }

    private fun Recipe.toFavouriteRecipe(): FavouriteRecipe =
        FavouriteRecipe(
            title = title,
            estimatedDuration = estimatedDuration.toFavouriteDuration(),
            difficulty = difficulty.toFavouriteDifficulty(),
            servings = servings,
            ingredients = ingredients.map { it.toFavouriteIngredient() },
            instructions = instructions.map { it.toFavouriteInstruction() },
            comment = comment,
        )

    private fun Duration.toFavouriteDuration(): FavouriteDuration =
        FavouriteDuration(
            hours = hours,
            minutes = minutes,
        )

    private fun Difficulty.toFavouriteDifficulty(): FavouriteDifficulty =
        when (this) {
            Difficulty.EASY -> FavouriteDifficulty.EASY
            Difficulty.MEDIUM -> FavouriteDifficulty.MEDIUM
            Difficulty.HARD -> FavouriteDifficulty.HARD
        }

    private fun Ingredient.toFavouriteIngredient(): FavouriteIngredient =
        FavouriteIngredient(
            name = name,
            quantity = quantity,
            commonAllergen = commonAllergen,
        )

    private fun Instruction.toFavouriteInstruction(): FavouriteInstruction =
        FavouriteInstruction(
            title = title,
            detail = detail,
        )
}
