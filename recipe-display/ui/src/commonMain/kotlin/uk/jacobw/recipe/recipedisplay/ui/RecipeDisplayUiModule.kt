package uk.jacobw.recipe.recipedisplay.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import uk.jacobw.recipe.recipedisplay.domain.getRecipeDisplayDomainModules
import org.koin.core.module.Module as KoinModule

@ComponentScan
@Module
class RecipeDisplayUiModule

fun getRecipeDisplayUiModules(): List<KoinModule> = RecipeDisplayUiModule().module + getRecipeDisplayDomainModules()
