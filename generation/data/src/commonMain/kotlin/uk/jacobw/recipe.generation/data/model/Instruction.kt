package uk.jacobw.recipe.generation.data.model

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("A single instruction step in a recipe, including a brief title and a detailed description.")
data class Instruction(
    val title: String,
    val detail: String,
)
