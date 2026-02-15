package uk.jacobw.recipe.favourites.data.model

data class RecipeData(
    val title: String,
    val estimatedDuration: DurationData,
    val difficulty: DifficultyData,
    val servings: Int,
    val ingredients: List<IngredientData>,
    val instructions: List<InstructionData>,
    val comment: String?,
)

data class DurationData(
    val hours: Int,
    val minutes: Int,
)

enum class DifficultyData {
    EASY,
    MEDIUM,
    HARD,
}

data class IngredientData(
    val name: String,
    val quantity: String,
    val commonAllergen: Boolean,
)

data class InstructionData(
    val title: String,
    val detail: String,
)

data class SavedRecipeData(
    val contentHash: String,
    val recipe: RecipeData,
)
