package uk.jacobw.recipe.generation.domain.usecase

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import uk.jacobw.recipe.generation.data.RecipeGenerationRepository
import uk.jacobw.recipe.generation.data.RecipeStoreRepository
import uk.jacobw.recipe.generation.data.model.Difficulty
import uk.jacobw.recipe.generation.data.model.Duration
import uk.jacobw.recipe.generation.data.model.Ingredient
import uk.jacobw.recipe.generation.data.model.Instruction
import uk.jacobw.recipe.generation.data.model.RecipeOutput
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateAndStoreRecipeUseCaseTest {
    private val generatedRecipe =
        RecipeOutput(
            title = "Test Recipe",
            estimatedDuration = Duration(hours = 0, minutes = 20),
            difficulty = Difficulty.EASY,
            servings = 2,
            ingredients =
                listOf(
                    Ingredient(name = "Flour", quantity = "100g", commonAllergen = true),
                ),
            instructions =
                listOf(
                    Instruction(title = "Mix", detail = "Mix ingredients"),
                ),
            comment = null,
        )

    @Test
    fun `invoke generates recipe then stores recipe and emits id`() =
        runTest {
            val generationRepository =
                object : RecipeGenerationRepository {
                    override fun generateRecipe(userPrompt: String): Flow<RecipeOutput> {
                        assertEquals("quick bread", userPrompt)
                        return flowOf(generatedRecipe)
                    }
                }
            val storeRepository =
                object : RecipeStoreRepository {
                    override fun saveRecipe(recipe: RecipeOutput): Flow<String> {
                        assertEquals(generatedRecipe, recipe)
                        return flowOf("generated-id")
                    }

                    override fun observeRecipeById(recipeId: String): Flow<RecipeOutput?> = flowOf(null)
                }
            val sut = GenerateAndStoreRecipeUseCase(generationRepository, storeRepository)

            sut("quick bread").test {
                assertEquals("generated-id", awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `invoke returns error when generation fails`() =
        runTest {
            val generationRepository =
                object : RecipeGenerationRepository {
                    override fun generateRecipe(userPrompt: String): Flow<RecipeOutput> =
                        flow {
                            throw RuntimeException("generation failed")
                        }
                }
            val storeRepository =
                object : RecipeStoreRepository {
                    override fun saveRecipe(recipe: RecipeOutput): Flow<String> = flowOf("unused")

                    override fun observeRecipeById(recipeId: String): Flow<RecipeOutput?> = flowOf(null)
                }
            val sut = GenerateAndStoreRecipeUseCase(generationRepository, storeRepository)

            sut("prompt").test {
                assertEquals("generation failed", awaitError().message)
            }
        }

    @Test
    fun `invoke returns error when store fails`() =
        runTest {
            val generationRepository =
                object : RecipeGenerationRepository {
                    override fun generateRecipe(userPrompt: String): Flow<RecipeOutput> = flowOf(generatedRecipe)
                }
            val storeRepository =
                object : RecipeStoreRepository {
                    override fun saveRecipe(recipe: RecipeOutput): Flow<String> =
                        flow {
                            throw RuntimeException("store failed")
                        }

                    override fun observeRecipeById(recipeId: String): Flow<RecipeOutput?> = flowOf(null)
                }
            val sut = GenerateAndStoreRecipeUseCase(generationRepository, storeRepository)

            sut("prompt").test {
                assertEquals("store failed", awaitError().message)
            }
        }
}
