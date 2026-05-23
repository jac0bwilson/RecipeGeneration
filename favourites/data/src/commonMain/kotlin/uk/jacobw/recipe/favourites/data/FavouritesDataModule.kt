package uk.jacobw.recipe.favourites.data

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import uk.jacobw.recipe.favourites.data.local.FavouriteRecipeDao
import uk.jacobw.recipe.favourites.data.local.FavouritesDatabase
import uk.jacobw.recipe.favourites.data.local.getFavouritesDatabase
import uk.jacobw.recipe.favourites.data.local.getFavouritesDatabaseBuilder

@ComponentScan
@Module
class FavouritesDataModule {
    @Single
    fun provideFavouritesDatabaseInstance(): FavouritesDatabase =
        getFavouritesDatabase(
            builder = getFavouritesDatabaseBuilder(),
        )

    @Single
    fun provideFavouriteRecipeDao(database: FavouritesDatabase): FavouriteRecipeDao = database.favouriteRecipeDao()
}
