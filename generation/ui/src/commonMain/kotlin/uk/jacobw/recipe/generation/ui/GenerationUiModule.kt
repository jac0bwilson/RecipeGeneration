package uk.jacobw.recipe.generation.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.core.module.Module as KoinModule

@ComponentScan
@Module
class GenerationUiModule

fun getGenerationUiModules(): List<KoinModule> = listOf(GenerationUiModule().module)
