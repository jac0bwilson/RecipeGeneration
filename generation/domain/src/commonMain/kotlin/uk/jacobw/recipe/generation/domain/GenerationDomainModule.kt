package uk.jacobw.recipe.generation.domain

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.generation.data.GenerationDataModule

@ComponentScan
@Module(includes = [GenerationDataModule::class])
class GenerationDomainModule
