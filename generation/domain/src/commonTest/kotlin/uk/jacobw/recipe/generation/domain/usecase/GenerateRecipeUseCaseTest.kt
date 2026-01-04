package uk.jacobw.recipe.generation.domain.usecase

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import uk.jacobw.recipe.generation.data.RecipeRepository
import uk.jacobw.recipe.generation.data.model.Difficulty
import uk.jacobw.recipe.generation.data.model.Duration
import uk.jacobw.recipe.generation.data.model.Ingredient
import uk.jacobw.recipe.generation.data.model.Instruction
import uk.jacobw.recipe.generation.data.model.RecipeOutput
import kotlin.test.Test
import kotlin.test.assertEquals
import uk.jacobw.recipe.generation.domain.model.Difficulty as DomainDifficulty
import uk.jacobw.recipe.generation.domain.model.Duration as DomainDuration
import uk.jacobw.recipe.generation.domain.model.Ingredient as DomainIngredient
import uk.jacobw.recipe.generation.domain.model.Instruction as DomainInstruction
import uk.jacobw.recipe.generation.domain.model.Recipe as DomainRecipe

class GenerateRecipeUseCaseTest {
    private val testRecipeOutput =
        RecipeOutput(
            title = "Test Recipe",
            estimatedDuration = Duration(hours = 1, minutes = 30),
            difficulty = Difficulty.MEDIUM,
            servings = 4,
            ingredients =
                listOf(
                    Ingredient(name = "Flour", quantity = "2 cups", commonAllergen = true),
                    Ingredient(name = "Sugar", quantity = "1 cup", commonAllergen = false),
                ),
            instructions =
                listOf(
                    Instruction(title = "Step 1", detail = "Mix flour and sugar"),
                    Instruction(title = "Step 2", detail = "Bake"),
                ),
            comment = "Enjoy!",
        )

    @Test
    fun `invoke returns mapped recipe from repository`() =
        runTest {
            val mockRecipeRepository =
                object : RecipeRepository {
                    override fun fetchRecipe(userPrompt: String) = flowOf(testRecipeOutput)
                }
            val sut = GenerateRecipeUseCase(mockRecipeRepository)

            sut("a prompt").test {
                val expectedRecipe =
                    DomainRecipe(
                        title = "Test Recipe",
                        estimatedDuration = DomainDuration(hours = 1, minutes = 30),
                        difficulty = DomainDifficulty.MEDIUM,
                        servings = 4,
                        ingredients =
                            listOf(
                                DomainIngredient(name = "Flour", quantity = "2 cups", commonAllergen = true),
                                DomainIngredient(name = "Sugar", quantity = "1 cup", commonAllergen = false),
                            ),
                        instructions =
                            listOf(
                                DomainInstruction(title = "Step 1", detail = "Mix flour and sugar"),
                                DomainInstruction(title = "Step 2", detail = "Bake"),
                            ),
                        comment = "Enjoy!",
                    )
                assertEquals(expectedRecipe, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `invoke returns error when repository throws exception`() =
        runTest {
            val mockRecipeRepository =
                object : RecipeRepository {
                    override fun fetchRecipe(userPrompt: String) =
                        flow<RecipeOutput> {
                            throw RuntimeException("An error occurred")
                        }
                }
            val sut = GenerateRecipeUseCase(mockRecipeRepository)

            sut("a prompt").test {
                val error = awaitError()
                assertEquals("An error occurred", error.message)
            }
        }
}
