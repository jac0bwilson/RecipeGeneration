package uk.jacobw.recipe

import uk.jacobw.recipe.favourites.domain.model.FavouriteDifficulty
import uk.jacobw.recipe.favourites.domain.model.FavouriteDuration
import uk.jacobw.recipe.favourites.domain.model.FavouriteIngredient
import uk.jacobw.recipe.favourites.domain.model.FavouriteInstruction
import uk.jacobw.recipe.favourites.domain.model.FavouriteRecipe
import uk.jacobw.recipe.generation.domain.model.Difficulty
import uk.jacobw.recipe.generation.domain.model.Duration
import uk.jacobw.recipe.generation.domain.model.Ingredient
import uk.jacobw.recipe.generation.domain.model.Instruction
import uk.jacobw.recipe.generation.domain.model.Recipe
import uk.jacobw.recipe.recipedisplay.ui.DisplayDifficulty
import uk.jacobw.recipe.recipedisplay.ui.DisplayDuration
import uk.jacobw.recipe.recipedisplay.ui.DisplayIngredient
import uk.jacobw.recipe.recipedisplay.ui.DisplayInstruction
import uk.jacobw.recipe.recipedisplay.ui.DisplayRecipe

internal fun Recipe.toDisplayRecipe(): DisplayRecipe =
    DisplayRecipe(
        title = title,
        estimatedDuration = estimatedDuration.toDisplayDuration(),
        difficulty = difficulty.toDisplayDifficulty(),
        servings = servings,
        ingredients = ingredients.map { it.toDisplayIngredient() },
        instructions = instructions.map { it.toDisplayInstruction() },
        comment = comment,
    )

internal fun FavouriteRecipe.toDisplayRecipe(): DisplayRecipe =
    DisplayRecipe(
        title = title,
        estimatedDuration = estimatedDuration.toDisplayDuration(),
        difficulty = difficulty.toDisplayDifficulty(),
        servings = servings,
        ingredients = ingredients.map { it.toDisplayIngredient() },
        instructions = instructions.map { it.toDisplayInstruction() },
        comment = comment,
    )

private fun Duration.toDisplayDuration(): DisplayDuration =
    DisplayDuration(
        hours = hours,
        minutes = minutes,
    )

private fun FavouriteDuration.toDisplayDuration(): DisplayDuration =
    DisplayDuration(
        hours = hours,
        minutes = minutes,
    )

private fun Difficulty.toDisplayDifficulty(): DisplayDifficulty =
    when (this) {
        Difficulty.EASY -> DisplayDifficulty.EASY
        Difficulty.MEDIUM -> DisplayDifficulty.MEDIUM
        Difficulty.HARD -> DisplayDifficulty.HARD
    }

private fun FavouriteDifficulty.toDisplayDifficulty(): DisplayDifficulty =
    when (this) {
        FavouriteDifficulty.EASY -> DisplayDifficulty.EASY
        FavouriteDifficulty.MEDIUM -> DisplayDifficulty.MEDIUM
        FavouriteDifficulty.HARD -> DisplayDifficulty.HARD
    }

private fun Ingredient.toDisplayIngredient(): DisplayIngredient =
    DisplayIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun FavouriteIngredient.toDisplayIngredient(): DisplayIngredient =
    DisplayIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun Instruction.toDisplayInstruction(): DisplayInstruction =
    DisplayInstruction(
        title = title,
        detail = detail,
    )

private fun FavouriteInstruction.toDisplayInstruction(): DisplayInstruction =
    DisplayInstruction(
        title = title,
        detail = detail,
    )
