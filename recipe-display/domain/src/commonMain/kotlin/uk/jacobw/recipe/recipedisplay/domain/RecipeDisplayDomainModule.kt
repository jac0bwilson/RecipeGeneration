package uk.jacobw.recipe.recipedisplay.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.favourites.data.FavouritesDataModule
import uk.jacobw.recipe.generation.data.GenerationDataModule

@ComponentScan
@Module(
    includes = [
        FavouritesDataModule::class,
        GenerationDataModule::class,
    ],
)
class RecipeDisplayDomainModule
