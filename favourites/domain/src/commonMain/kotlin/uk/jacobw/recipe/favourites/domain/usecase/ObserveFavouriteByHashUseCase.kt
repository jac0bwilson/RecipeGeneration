package uk.jacobw.recipe.favourites.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import uk.jacobw.recipe.favourites.data.FavouritesRepository
import uk.jacobw.recipe.favourites.domain.model.SavedRecipe

@Factory
class ObserveFavouriteByHashUseCase(
    private val favouritesRepository: FavouritesRepository,
) {
    operator fun invoke(contentHash: String): Flow<SavedRecipe?> =
        favouritesRepository.observeFavouriteByContentHash(contentHash).map { favourite ->
            favourite?.toDomain()
        }
}
