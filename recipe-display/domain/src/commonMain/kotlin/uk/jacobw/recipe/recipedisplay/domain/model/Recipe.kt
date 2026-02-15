package uk.jacobw.recipe.recipedisplay.domain.model

data class Recipe(
    val title: String,
    val estimatedDuration: Duration,
    val difficulty: Difficulty,
    val servings: Int,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val comment: String?,
)

sealed interface RecipeReference {
    data class Generated(
        val recipeId: String,
    ) : RecipeReference

    data class Favourite(
        val contentHash: String,
    ) : RecipeReference
}

data class Duration(
    val hours: Int,
    val minutes: Int,
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
}

data class Ingredient(
    val name: String,
    val quantity: String,
    val commonAllergen: Boolean,
)

data class Instruction(
    val title: String,
    val detail: String,
)
