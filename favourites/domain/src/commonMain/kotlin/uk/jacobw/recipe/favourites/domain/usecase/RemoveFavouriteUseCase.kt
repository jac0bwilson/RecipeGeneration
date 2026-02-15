package uk.jacobw.recipe.favourites.domain.usecase

import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository

@Factory
class RemoveFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    suspend operator fun invoke(contentHash: String) {
        favouritesRepository.removeFavouriteByContentHash(contentHash)
    }
}
