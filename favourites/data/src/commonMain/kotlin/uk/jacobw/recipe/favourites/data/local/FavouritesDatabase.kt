package uk.jacobw.recipe.favourites.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [FavouriteRecipeEntity::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(FavouritesDatabaseConstructor::class)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object FavouritesDatabaseConstructor : RoomDatabaseConstructor<FavouritesDatabase> {
    override fun initialize(): FavouritesDatabase
}
