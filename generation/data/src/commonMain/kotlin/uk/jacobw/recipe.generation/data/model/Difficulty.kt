package uk.jacobw.recipe.generation.data.model

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("The difficulty level of the recipe, indicating how challenging it is to prepare.")
enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
}
