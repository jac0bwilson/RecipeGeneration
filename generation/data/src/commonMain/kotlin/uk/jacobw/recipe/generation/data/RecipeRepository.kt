package uk.jacobw.recipe.generation.data

import kotlinx.coroutines.flow.Flow
import uk.jacobw.recipe.generation.data.model.RecipeOutput

interface RecipeRepository {
    fun fetchRecipe(userPrompt: String): Flow<RecipeOutput>
}
