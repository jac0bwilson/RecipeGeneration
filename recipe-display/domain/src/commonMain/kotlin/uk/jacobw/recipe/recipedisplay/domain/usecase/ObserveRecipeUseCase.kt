package uk.jacobw.recipe.recipedisplay.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.generation.data.RecipeStoreRepository
import uk.jacobw.recipe.recipedisplay.domain.model.Recipe
import uk.jacobw.recipe.recipedisplay.domain.model.RecipeReference

@Factory
class ObserveRecipeUseCase(
    private val recipeStoreRepository: RecipeStoreRepository,
    private val favouritesRepository: FavouritesRepository,
) {
    operator fun invoke(reference: RecipeReference): Flow<Recipe?> =
        when (reference) {
            is RecipeReference.Generated ->
                recipeStoreRepository.observeRecipeById(reference.recipeId).map { recipe ->
                    recipe?.toDomainRecipe()
                }

            is RecipeReference.Favourite ->
                favouritesRepository.observeFavouriteByContentHash(reference.contentHash).map { favourite ->
                    favourite?.recipe?.toDomainRecipe()
                }
        }
}
