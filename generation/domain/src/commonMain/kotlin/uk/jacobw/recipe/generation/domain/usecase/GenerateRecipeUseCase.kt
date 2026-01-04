package uk.jacobw.recipe.generation.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.generation.data.RecipeRepository
import uk.jacobw.recipe.generation.data.model.Difficulty
import uk.jacobw.recipe.generation.data.model.Duration
import uk.jacobw.recipe.generation.data.model.Ingredient
import uk.jacobw.recipe.generation.data.model.Instruction
import uk.jacobw.recipe.generation.domain.model.Recipe
import uk.jacobw.recipe.generation.domain.model.Difficulty as DomainDifficulty
import uk.jacobw.recipe.generation.domain.model.Duration as DomainDuration
import uk.jacobw.recipe.generation.domain.model.Ingredient as DomainIngredient
import uk.jacobw.recipe.generation.domain.model.Instruction as DomainInstruction

@Factory
class GenerateRecipeUseCase(
    private val recipeRepository: RecipeRepository,
) {
    operator fun invoke(prompt: String): Flow<Recipe> =
        recipeRepository
            .fetchRecipe(prompt)
            .map { recipe ->
                Recipe(
                    title = recipe.title,
                    estimatedDuration = recipe.estimatedDuration.toDomain(),
                    difficulty = recipe.difficulty.toDomain(),
                    servings = recipe.servings,
                    ingredients = recipe.ingredients.map { it.toDomain() },
                    instructions = recipe.instructions.map { it.toDomain() },
                    comment = recipe.comment,
                )
            }

    private fun Duration.toDomain(): DomainDuration =
        DomainDuration(
            hours = this.hours,
            minutes = this.minutes,
        )

    private fun Difficulty.toDomain(): DomainDifficulty =
        when (this) {
            Difficulty.EASY -> DomainDifficulty.EASY
            Difficulty.MEDIUM -> DomainDifficulty.MEDIUM
            Difficulty.HARD -> DomainDifficulty.HARD
        }

    private fun Ingredient.toDomain(): DomainIngredient =
        DomainIngredient(
            name = name,
            quantity = quantity,
            commonAllergen = commonAllergen,
        )

    private fun Instruction.toDomain(): DomainInstruction =
        DomainInstruction(
            title = title,
            detail = detail,
        )
}
