package uk.jacobw.recipe.recipedisplay.domain.usecase

import uk.jacobw.recipe.favourites.data.model.DifficultyData
import uk.jacobw.recipe.favourites.data.model.DurationData
import uk.jacobw.recipe.favourites.data.model.IngredientData
import uk.jacobw.recipe.favourites.data.model.InstructionData
import uk.jacobw.recipe.favourites.data.model.RecipeData
import uk.jacobw.recipe.generation.data.model.Difficulty
import uk.jacobw.recipe.generation.data.model.Duration
import uk.jacobw.recipe.generation.data.model.Ingredient
import uk.jacobw.recipe.generation.data.model.Instruction
import uk.jacobw.recipe.generation.data.model.RecipeOutput
import uk.jacobw.recipe.recipedisplay.domain.model.Recipe
import uk.jacobw.recipe.recipedisplay.domain.model.Difficulty as DomainDifficulty
import uk.jacobw.recipe.recipedisplay.domain.model.Duration as DomainDuration
import uk.jacobw.recipe.recipedisplay.domain.model.Ingredient as DomainIngredient
import uk.jacobw.recipe.recipedisplay.domain.model.Instruction as DomainInstruction

internal fun RecipeOutput.toDomainRecipe(): Recipe =
    Recipe(
        title = title,
        estimatedDuration = estimatedDuration.toDomain(),
        difficulty = difficulty.toDomain(),
        servings = servings,
        ingredients = ingredients.map { it.toDomain() },
        instructions = instructions.map { it.toDomain() },
        comment = comment,
    )

internal fun Recipe.toFavouriteData(): RecipeData =
    RecipeData(
        title = title,
        estimatedDuration = estimatedDuration.toFavouriteData(),
        difficulty = difficulty.toFavouriteData(),
        servings = servings,
        ingredients = ingredients.map { it.toFavouriteData() },
        instructions = instructions.map { it.toFavouriteData() },
        comment = comment,
    )

internal fun RecipeData.toDomainRecipe(): Recipe =
    Recipe(
        title = title,
        estimatedDuration = estimatedDuration.toDomain(),
        difficulty = difficulty.toDomain(),
        servings = servings,
        ingredients = ingredients.map { it.toDomain() },
        instructions = instructions.map { it.toDomain() },
        comment = comment,
    )

private fun Duration.toDomain(): DomainDuration =
    DomainDuration(
        hours = hours,
        minutes = minutes,
    )

private fun DurationData.toDomain(): DomainDuration =
    DomainDuration(
        hours = hours,
        minutes = minutes,
    )

private fun Difficulty.toDomain(): DomainDifficulty =
    when (this) {
        Difficulty.EASY -> DomainDifficulty.EASY
        Difficulty.MEDIUM -> DomainDifficulty.MEDIUM
        Difficulty.HARD -> DomainDifficulty.HARD
    }

private fun DifficultyData.toDomain(): DomainDifficulty =
    when (this) {
        DifficultyData.EASY -> DomainDifficulty.EASY
        DifficultyData.MEDIUM -> DomainDifficulty.MEDIUM
        DifficultyData.HARD -> DomainDifficulty.HARD
    }

private fun Ingredient.toDomain(): DomainIngredient =
    DomainIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun IngredientData.toDomain(): DomainIngredient =
    DomainIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun Instruction.toDomain(): DomainInstruction =
    DomainInstruction(
        title = title,
        detail = detail,
    )

private fun InstructionData.toDomain(): DomainInstruction =
    DomainInstruction(
        title = title,
        detail = detail,
    )

private fun DomainDuration.toFavouriteData(): DurationData =
    DurationData(
        hours = hours,
        minutes = minutes,
    )

private fun DomainDifficulty.toFavouriteData(): DifficultyData =
    when (this) {
        DomainDifficulty.EASY -> DifficultyData.EASY
        DomainDifficulty.MEDIUM -> DifficultyData.MEDIUM
        DomainDifficulty.HARD -> DifficultyData.HARD
    }

private fun DomainIngredient.toFavouriteData(): IngredientData =
    IngredientData(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun DomainInstruction.toFavouriteData(): InstructionData =
    InstructionData(
        title = title,
        detail = detail,
    )
