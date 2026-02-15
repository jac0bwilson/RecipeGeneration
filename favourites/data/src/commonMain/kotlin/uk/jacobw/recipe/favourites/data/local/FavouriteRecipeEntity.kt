package uk.jacobw.recipe.favourites.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourite_recipes",
    indices = [Index(value = ["contentHash"], unique = true)],
)
data class FavouriteRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val contentHash: String,
    val title: String,
    val durationHours: Int,
    val durationMinutes: Int,
    val difficulty: String,
    val servings: Int,
    val ingredientsJson: String,
    val instructionsJson: String,
    val comment: String?,
)
