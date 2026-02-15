package uk.jacobw.recipe.favourites.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.favourites.data.model.DifficultyData
import uk.jacobw.recipe.favourites.data.model.DurationData
import uk.jacobw.recipe.favourites.data.model.IngredientData
import uk.jacobw.recipe.favourites.data.model.InstructionData
import uk.jacobw.recipe.favourites.data.model.RecipeData
import uk.jacobw.recipe.favourites.data.model.SavedRecipeData
import uk.jacobw.recipe.favourites.domain.model.FavouriteDifficulty
import uk.jacobw.recipe.favourites.domain.model.FavouriteDuration
import uk.jacobw.recipe.favourites.domain.model.FavouriteIngredient
import uk.jacobw.recipe.favourites.domain.model.FavouriteInstruction
import uk.jacobw.recipe.favourites.domain.model.FavouriteRecipe
import uk.jacobw.recipe.favourites.domain.model.SavedRecipe

@Factory
class ObserveFavouritesUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    operator fun invoke(): Flow<List<SavedRecipe>> =
        favouritesRepository.observeFavourites().map { savedRecipes ->
            savedRecipes.map { it.toDomain() }
        }
}

internal fun SavedRecipeData.toDomain(): SavedRecipe =
    SavedRecipe(
        contentHash = contentHash,
        recipe = recipe.toDomain(),
    )

internal fun FavouriteRecipe.toData(): RecipeData =
    RecipeData(
        title = title,
        estimatedDuration = estimatedDuration.toData(),
        difficulty = difficulty.toData(),
        servings = servings,
        ingredients = ingredients.map { it.toData() },
        instructions = instructions.map { it.toData() },
        comment = comment,
    )

private fun RecipeData.toDomain(): FavouriteRecipe =
    FavouriteRecipe(
        title = title,
        estimatedDuration = estimatedDuration.toDomain(),
        difficulty = difficulty.toDomain(),
        servings = servings,
        ingredients = ingredients.map { it.toDomain() },
        instructions = instructions.map { it.toDomain() },
        comment = comment,
    )

private fun DurationData.toDomain(): FavouriteDuration =
    FavouriteDuration(
        hours = hours,
        minutes = minutes,
    )

private fun DifficultyData.toDomain(): FavouriteDifficulty =
    when (this) {
        DifficultyData.EASY -> FavouriteDifficulty.EASY
        DifficultyData.MEDIUM -> FavouriteDifficulty.MEDIUM
        DifficultyData.HARD -> FavouriteDifficulty.HARD
    }

private fun IngredientData.toDomain(): FavouriteIngredient =
    FavouriteIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun InstructionData.toDomain(): FavouriteInstruction =
    FavouriteInstruction(
        title = title,
        detail = detail,
    )

private fun FavouriteDuration.toData(): DurationData =
    DurationData(
        hours = hours,
        minutes = minutes,
    )

private fun FavouriteDifficulty.toData(): DifficultyData =
    when (this) {
        FavouriteDifficulty.EASY -> DifficultyData.EASY
        FavouriteDifficulty.MEDIUM -> DifficultyData.MEDIUM
        FavouriteDifficulty.HARD -> DifficultyData.HARD
    }

private fun FavouriteIngredient.toData(): IngredientData =
    IngredientData(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun FavouriteInstruction.toData(): InstructionData =
    InstructionData(
        title = title,
        detail = detail,
    )
