package uk.jacobw.recipe.favourites.domain.model

data class SavedRecipe(
    val contentHash: String,
    val recipe: FavouriteRecipe,
)

data class FavouriteRecipe(
    val title: String,
    val estimatedDuration: FavouriteDuration,
    val difficulty: FavouriteDifficulty,
    val servings: Int,
    val ingredients: List<FavouriteIngredient>,
    val instructions: List<FavouriteInstruction>,
    val comment: String?,
)

data class FavouriteDuration(
    val hours: Int,
    val minutes: Int,
)

enum class FavouriteDifficulty {
    EASY,
    MEDIUM,
    HARD,
}

data class FavouriteIngredient(
    val name: String,
    val quantity: String,
    val commonAllergen: Boolean,
)

data class FavouriteInstruction(
    val title: String,
    val detail: String,
)
