package uk.jacobw.recipe.generation.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import uk.jacobw.recipe.generation.domain.GenerationDomainModule

@ComponentScan
@Module(includes = [GenerationDomainModule::class])
class GenerationUiModule
