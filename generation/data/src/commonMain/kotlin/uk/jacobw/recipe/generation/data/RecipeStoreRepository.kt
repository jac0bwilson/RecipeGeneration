package uk.jacobw.recipe.generation.data

import kotlinx.coroutines.flow.Flow
import uk.jacobw.recipe.generation.data.model.RecipeOutput

interface RecipeStoreRepository {
    fun saveRecipe(recipe: RecipeOutput): Flow<String>

    fun observeRecipeById(recipeId: String): Flow<RecipeOutput?>
}
