package uk.jacobw.recipe.generation.data.model

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("A recipe output containing all necessary details to prepare a dish.")
data class RecipeOutput(
    @property:LLMDescription("A brief and descriptive title of the recipe.")
    val title: String,
    val estimatedDuration: Duration,
    val difficulty: Difficulty,
    @property:LLMDescription("The number of servings the recipe yields.")
    val servings: Int,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    @property:LLMDescription(
        "Additional optional comments or tips related to the recipe, which are not already covered in the instructions.",
    )
    val comment: String?,
)
