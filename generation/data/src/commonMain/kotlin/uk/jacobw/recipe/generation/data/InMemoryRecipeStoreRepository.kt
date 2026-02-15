package uk.jacobw.recipe.generation.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Single
import uk.jacobw.recipe.generation.data.model.RecipeOutput
import kotlin.random.Random

@Single
class InMemoryRecipeStoreRepository : RecipeStoreRepository {
    private val recipes = MutableStateFlow<Map<String, RecipeOutput>>(emptyMap())

    override fun saveRecipe(recipe: RecipeOutput): Flow<String> {
        val recipeId = "${Random.nextLong()}-${Random.nextLong()}"

        recipes.update { current ->
            current + (recipeId to recipe)
        }

        return flowOf(recipeId)
    }

    override fun observeRecipeById(recipeId: String): Flow<RecipeOutput?> =
        recipes.map { current ->
            current[recipeId]
        }
}
