package uk.jacobw.recipe.generation.data.model

import ai.koog.agents.core.tools.annotations.LLMDescription
import kotlinx.serialization.Serializable

@Serializable
@LLMDescription("The time required to complete preparation and cooking of the dish, specified in hours and minutes.")
data class Duration(
    val hours: Int,
    val minutes: Int,
)
