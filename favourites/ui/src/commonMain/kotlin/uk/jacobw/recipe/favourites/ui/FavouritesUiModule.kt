package uk.jacobw.recipe.favourites.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.favourites.domain.FavouritesDomainModule

@ComponentScan
@Module(includes = [FavouritesDomainModule::class])
class FavouritesUiModule
