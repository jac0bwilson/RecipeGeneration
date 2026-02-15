package uk.jacobw.recipe.recipedisplay.ui

data class DisplayRecipe(
    val title: String,
    val estimatedDuration: DisplayDuration,
    val difficulty: DisplayDifficulty,
    val servings: Int,
    val ingredients: List<DisplayIngredient>,
    val instructions: List<DisplayInstruction>,
    val comment: String?,
)

data class DisplayDuration(
    val hours: Int,
    val minutes: Int,
)

enum class DisplayDifficulty {
    EASY,
    MEDIUM,
    HARD,
}

data class DisplayIngredient(
    val name: String,
    val quantity: String,
    val commonAllergen: Boolean,
)

data class DisplayInstruction(
    val title: String,
    val detail: String,
)
