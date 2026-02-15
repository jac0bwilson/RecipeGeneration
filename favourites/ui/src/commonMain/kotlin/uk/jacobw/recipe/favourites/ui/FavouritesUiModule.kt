package uk.jacobw.recipe.favourites.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import uk.jacobw.recipe.favourites.domain.getFavouritesDomainModules
import org.koin.core.module.Module as KoinModule

@ComponentScan
@Module
class FavouritesUiModule

fun getFavouritesUiModules(): List<KoinModule> = FavouritesUiModule().module + getFavouritesDomainModules()
