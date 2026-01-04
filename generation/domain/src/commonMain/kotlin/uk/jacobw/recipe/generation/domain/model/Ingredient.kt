package uk.jacobw.recipe.generation.domain.model

data class Ingredient(
    val name: String,
    val quantity: String,
    val commonAllergen: Boolean,
)
