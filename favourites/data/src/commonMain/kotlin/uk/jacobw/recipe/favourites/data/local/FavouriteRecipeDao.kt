package uk.jacobw.recipe.favourites.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {
    @Query("SELECT * FROM favourite_recipes ORDER BY title ASC")
    fun observeAllSortedByTitle(): Flow<List<FavouriteRecipeEntity>>

    @Query("SELECT * FROM favourite_recipes WHERE contentHash = :contentHash LIMIT 1")
    fun observeByContentHash(contentHash: String): Flow<FavouriteRecipeEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: FavouriteRecipeEntity): Long

    @Query("DELETE FROM favourite_recipes WHERE contentHash = :contentHash")
    suspend fun deleteByContentHash(contentHash: String): Int
}
