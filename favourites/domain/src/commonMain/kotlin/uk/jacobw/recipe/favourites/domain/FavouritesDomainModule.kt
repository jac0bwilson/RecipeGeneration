package uk.jacobw.recipe.favourites.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import uk.jacobw.recipe.favourites.data.getFavouritesDataModules

@ComponentScan
@Module
class FavouritesDomainModule

fun getFavouritesDomainModules() = listOf(FavouritesDomainModule().module) + getFavouritesDataModules()
