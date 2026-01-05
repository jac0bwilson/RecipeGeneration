package uk.jacobw.recipe.generation.data

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.model.PromptExecutor
import ai.koog.prompt.structure.executeStructured
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import uk.jacobw.recipe.generation.data.model.RecipeOutput

@Single
class GeneratedRecipeRepository(
    private val promptExecutor: PromptExecutor,
) : RecipeRepository {
    override fun fetchRecipe(userPrompt: String): Flow<RecipeOutput> =
        flow {
            promptExecutor
                .executeStructured<RecipeOutput>(
                    prompt =
                        prompt("recipe") {
                            system(SYSTEM_PROMPT.trimIndent())
                            user(userPrompt)
                        },
                    model = GoogleModels.Gemini2_5Flash,
                ).fold(
                    onSuccess = {
                        emit(it.data)
                    },
                    onFailure = {
                        throw it
                    },
                )
        }

    private companion object {
        const val SYSTEM_PROMPT: String = """
            You are a curator of recipes and culinary expert.
            
            Given an input which may include ingredients, equipment, dietary preferences, cuisine types or cooking methods - you will return a recipe which best matches what the user has asked for.
            
            When providing units of measurement, use metric and ensure that the same symbols are used for each ingredient which requires them. For example, use "g" for grams and "ml" for milliliters consistently.
            
            Return all string contents in plain text.
        """
    }
}
