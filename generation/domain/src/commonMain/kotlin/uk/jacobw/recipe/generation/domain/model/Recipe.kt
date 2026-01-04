package uk.jacobw.recipe.generation.domain.model

data class Recipe(
    val title: String,
    val estimatedDuration: Duration,
    val difficulty: Difficulty,
    val servings: Int,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val comment: String?,
)
