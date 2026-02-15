package uk.jacobw.recipe.favourites.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal fun getFavouritesDatabase(builder: RoomDatabase.Builder<FavouritesDatabase>): FavouritesDatabase =
    builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(true)
        .build()

internal expect fun getFavouritesDatabaseBuilder(): RoomDatabase.Builder<FavouritesDatabase>
