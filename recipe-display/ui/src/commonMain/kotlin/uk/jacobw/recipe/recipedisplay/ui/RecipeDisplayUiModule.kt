package uk.jacobw.recipe.recipedisplay.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.recipedisplay.domain.RecipeDisplayDomainModule

@ComponentScan
@Module(includes = [RecipeDisplayDomainModule::class])
class RecipeDisplayUiModule
