package uk.jacobw.recipe.recipedisplay.ui

import uk.jacobw.recipe.recipedisplay.domain.model.Difficulty
import uk.jacobw.recipe.recipedisplay.domain.model.Duration
import uk.jacobw.recipe.recipedisplay.domain.model.Ingredient
import uk.jacobw.recipe.recipedisplay.domain.model.Instruction
import uk.jacobw.recipe.recipedisplay.domain.model.Recipe

internal fun Recipe.toUiModel(): DisplayRecipe =
    DisplayRecipe(
        title = title,
        estimatedDuration = estimatedDuration.toUiModel(),
        difficulty = difficulty.toUiModel(),
        servings = servings,
        ingredients = ingredients.map { it.toUiModel() },
        instructions = instructions.map { it.toUiModel() },
        comment = comment,
    )

private fun Duration.toUiModel(): DisplayDuration =
    DisplayDuration(
        hours = hours,
        minutes = minutes,
    )

private fun Difficulty.toUiModel(): DisplayDifficulty =
    when (this) {
        Difficulty.EASY -> DisplayDifficulty.EASY
        Difficulty.MEDIUM -> DisplayDifficulty.MEDIUM
        Difficulty.HARD -> DisplayDifficulty.HARD
    }

private fun Ingredient.toUiModel(): DisplayIngredient =
    DisplayIngredient(
        name = name,
        quantity = quantity,
        commonAllergen = commonAllergen,
    )

private fun Instruction.toUiModel(): DisplayInstruction =
    DisplayInstruction(
        title = title,
        detail = detail,
    )
