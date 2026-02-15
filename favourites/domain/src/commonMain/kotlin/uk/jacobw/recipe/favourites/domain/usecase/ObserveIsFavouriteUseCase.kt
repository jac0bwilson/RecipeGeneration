package uk.jacobw.recipe.favourites.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.favourites.domain.model.FavouriteRecipe

@Factory
class ObserveIsFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    operator fun invoke(recipe: FavouriteRecipe): Flow<Boolean> =
        favouritesRepository.observeIsFavourite(recipe.toData())
}
