package uk.jacobw.recipe.favourites.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.context.GlobalContext

internal actual fun getFavouritesDatabaseBuilder(): RoomDatabase.Builder<FavouritesDatabase> {
    val appContext = GlobalContext.get().get<Context>().applicationContext
    val dbFile = appContext.getDatabasePath("favourites.db")

    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
