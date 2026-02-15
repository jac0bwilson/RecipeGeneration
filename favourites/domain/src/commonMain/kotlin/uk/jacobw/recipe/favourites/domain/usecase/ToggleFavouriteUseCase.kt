package uk.jacobw.recipe.favourites.domain.usecase

import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.favourites.domain.model.FavouriteRecipe

@Factory
class ToggleFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    suspend operator fun invoke(recipe: FavouriteRecipe) {
        favouritesRepository.toggleFavourite(recipe.toData())
    }
}
