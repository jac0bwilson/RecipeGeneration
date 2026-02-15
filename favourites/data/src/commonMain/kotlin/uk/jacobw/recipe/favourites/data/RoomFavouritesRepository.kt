package uk.jacobw.recipe.favourites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import uk.jacobw.recipe.favourites.data.local.FavouriteRecipeDao
import uk.jacobw.recipe.favourites.data.local.FavouriteRecipeEntity
import uk.jacobw.recipe.favourites.data.model.DifficultyData
import uk.jacobw.recipe.favourites.data.model.DurationData
import uk.jacobw.recipe.favourites.data.model.IngredientData
import uk.jacobw.recipe.favourites.data.model.InstructionData
import uk.jacobw.recipe.favourites.data.model.RecipeData
import uk.jacobw.recipe.favourites.data.model.SavedRecipeData

@Single
class RoomFavouritesRepository(
    private val favouriteRecipeDao: FavouriteRecipeDao,
) : FavouritesRepository {
    private val json = Json { ignoreUnknownKeys = true }

    override fun observeFavourites(): Flow<List<SavedRecipeData>> =
        favouriteRecipeDao.observeAllSortedByTitle().map { entities ->
            entities.map { it.toSavedRecipeData(json) }
        }

    override fun observeFavouriteByContentHash(contentHash: String): Flow<SavedRecipeData?> =
        favouriteRecipeDao.observeByContentHash(contentHash).map { entity ->
            entity?.toSavedRecipeData(json)
        }

    override fun observeIsFavourite(recipe: RecipeData): Flow<Boolean> =
        favouriteRecipeDao.observeByContentHash(recipe.contentHash()).map { entity ->
            entity != null
        }

    override suspend fun toggleFavourite(recipe: RecipeData) {
        val contentHash = recipe.contentHash()
        val existingRecipe = favouriteRecipeDao.observeByContentHash(contentHash).first()

        if (existingRecipe == null) {
            favouriteRecipeDao.insertIgnore(
                entity = recipe.toEntity(contentHash, json),
            )
            return
        }

        favouriteRecipeDao.deleteByContentHash(contentHash)
    }

    override suspend fun removeFavouriteByContentHash(contentHash: String) {
        favouriteRecipeDao.deleteByContentHash(contentHash)
    }

    private fun RecipeData.toEntity(
        contentHash: String,
        json: Json,
    ): FavouriteRecipeEntity =
        FavouriteRecipeEntity(
            contentHash = contentHash,
            title = title,
            durationHours = estimatedDuration.hours,
            durationMinutes = estimatedDuration.minutes,
            difficulty = difficulty.name,
            servings = servings,
            ingredientsJson =
                json.encodeToString(
                    ingredients.map {
                        StoredIngredient(
                            name = it.name,
                            quantity = it.quantity,
                            commonAllergen = it.commonAllergen,
                        )
                    },
                ),
            instructionsJson =
                json.encodeToString(
                    instructions.map {
                        StoredInstruction(
                            title = it.title,
                            detail = it.detail,
                        )
                    },
                ),
            comment = comment,
        )

    private fun FavouriteRecipeEntity.toSavedRecipeData(json: Json): SavedRecipeData =
        SavedRecipeData(
            contentHash = contentHash,
            recipe =
                RecipeData(
                    title = title,
                    estimatedDuration = DurationData(hours = durationHours, minutes = durationMinutes),
                    difficulty =
                        runCatching { DifficultyData.valueOf(difficulty) }
                            .getOrDefault(DifficultyData.MEDIUM),
                    servings = servings,
                    ingredients =
                        json.decodeFromString<List<StoredIngredient>>(ingredientsJson).map {
                            IngredientData(
                                name = it.name,
                                quantity = it.quantity,
                                commonAllergen = it.commonAllergen,
                            )
                        },
                    instructions =
                        json.decodeFromString<List<StoredInstruction>>(instructionsJson).map {
                            InstructionData(
                                title = it.title,
                                detail = it.detail,
                            )
                        },
                    comment = comment,
                ),
        )

    private fun RecipeData.contentHash(): String = canonicalPayload().sha256Hex()

    private fun RecipeData.canonicalPayload(): String =
        buildString {
            appendField(title)
            appendField(estimatedDuration.hours.toString())
            appendField(estimatedDuration.minutes.toString())
            appendField(difficulty.name)
            appendField(servings.toString())
            appendField(comment ?: "<null>")

            appendField(ingredients.size.toString())
            ingredients.forEach { ingredient ->
                appendField(ingredient.name)
                appendField(ingredient.quantity)
                appendField(ingredient.commonAllergen.toString())
            }

            appendField(instructions.size.toString())
            instructions.forEach { instruction ->
                appendField(instruction.title)
                appendField(instruction.detail)
            }
        }

    private fun StringBuilder.appendField(value: String) {
        append(value.length)
        append(':')
        append(value)
    }

    @Serializable
    private data class StoredIngredient(
        val name: String,
        val quantity: String,
        val commonAllergen: Boolean,
    )

    @Serializable
    private data class StoredInstruction(
        val title: String,
        val detail: String,
    )
}
