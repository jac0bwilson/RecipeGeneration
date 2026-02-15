package uk.jacobw.recipe.favourites.data

import kotlinx.coroutines.flow.Flow
import uk.jacobw.recipe.favourites.data.model.RecipeData
import uk.jacobw.recipe.favourites.data.model.SavedRecipeData

interface FavouritesRepository {
    fun observeFavourites(): Flow<List<SavedRecipeData>>

    fun observeFavouriteByContentHash(contentHash: String): Flow<SavedRecipeData?>

    fun observeIsFavourite(recipe: RecipeData): Flow<Boolean>

    suspend fun toggleFavourite(recipe: RecipeData)

    suspend fun removeFavouriteByContentHash(contentHash: String)
}
