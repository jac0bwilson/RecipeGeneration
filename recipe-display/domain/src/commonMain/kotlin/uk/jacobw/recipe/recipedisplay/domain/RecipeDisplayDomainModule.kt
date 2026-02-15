package uk.jacobw.recipe.recipedisplay.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.core.module.Module as KoinModule

@ComponentScan
@Module
class RecipeDisplayDomainModule

fun getRecipeDisplayDomainModules(): List<KoinModule> = listOf(RecipeDisplayDomainModule().module)
