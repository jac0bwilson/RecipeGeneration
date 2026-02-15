package uk.jacobw.recipe.recipedisplay.domain.usecase

import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.recipedisplay.domain.model.Recipe

@Factory
class ToggleFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    suspend operator fun invoke(recipe: Recipe) {
        favouritesRepository.toggleFavourite(recipe.toFavouriteData())
    }
}
