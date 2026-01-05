package uk.jacobw.recipe.generation.data.model

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("An ingredient required for a recipe, including its name and quantity.")
data class Ingredient(
    val name: String,
    @property:LLMDescription("The amount of the ingredient needed, including units of measurement.")
    val quantity: String,
    @property:LLMDescription("Indicates whether the ingredient is a common allergen.")
    val commonAllergen: Boolean,
)
