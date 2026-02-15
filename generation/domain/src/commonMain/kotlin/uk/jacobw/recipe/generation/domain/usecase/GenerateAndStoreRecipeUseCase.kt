package uk.jacobw.recipe.generation.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.generation.data.RecipeGenerationRepository
import uk.jacobw.recipe.generation.data.RecipeStoreRepository

@Factory
class GenerateAndStoreRecipeUseCase(
    private val recipeGenerationRepository: RecipeGenerationRepository,
    private val recipeStoreRepository: RecipeStoreRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(prompt: String): Flow<String> =
        recipeGenerationRepository.generateRecipe(prompt).flatMapConcat { generatedRecipe ->
            recipeStoreRepository.saveRecipe(generatedRecipe)
        }
}
