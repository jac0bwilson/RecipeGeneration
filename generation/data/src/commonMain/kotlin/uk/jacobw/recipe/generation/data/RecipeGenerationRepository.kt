package uk.jacobw.recipe.generation.data

import kotlinx.coroutines.flow.Flow
import uk.jacobw.recipe.generation.data.model.RecipeOutput

interface RecipeGenerationRepository {
    fun generateRecipe(userPrompt: String): Flow<RecipeOutput>
}
