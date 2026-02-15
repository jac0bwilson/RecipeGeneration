package uk.jacobw.recipe.recipedisplay.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.recipedisplay.domain.model.Recipe

@Factory
class ObserveIsFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    operator fun invoke(recipe: Recipe): Flow<Boolean> = favouritesRepository.observeIsFavourite(recipe.toFavouriteData())
}
