package uk.jacobw.recipe.generation.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import uk.jacobw.recipe.generation.data.GenerationDataModule

@ComponentScan
@Module
class GenerationDomainModule

fun getGenerationDomainModules() = listOf(GenerationDomainModule().module, GenerationDataModule().module)
