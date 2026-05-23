package uk.jacobw.recipe.favourites.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.favourites.data.FavouritesDataModule

@ComponentScan
@Module(includes = [FavouritesDataModule::class])
class FavouritesDomainModule
