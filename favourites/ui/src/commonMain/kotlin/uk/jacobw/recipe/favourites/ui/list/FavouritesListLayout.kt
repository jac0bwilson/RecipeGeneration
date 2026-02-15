package uk.jacobw.recipe.favourites.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import uk.jacobw.recipe.core.ui.component.Title
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavouritesListLayout(
    favourites: List<FavouriteListItem>,
    onRecipeSelected: (String) -> Unit,
    onRemoveFavourite: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { internalPadding ->
        Column(
            modifier =
                Modifier
                    .imePadding()
                    .padding(internalPadding)
                    .padding(16.dp)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Title(
                text = "Favourites"
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (favourites.isEmpty()) {
                    EmptyFavourites()
                    return@Scaffold
                }

                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(favourites, key = { it.contentHash }) { favourite ->
                        FavouriteRecipe(favourite, onRecipeSelected, onRemoveFavourite)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavouriteRecipe(
    favourite: FavouriteListItem,
    onRecipeSelected: (String) -> Unit,
    onRemoveFavourite: (String) -> Unit
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onRecipeSelected(favourite.contentHash)
                },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = favourite.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "${favourite.servings} servings",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            IconButton(
                onClick = {
                    onRemoveFavourite(favourite.contentHash)
                },
            ) {
                Text("Remove")
            }
        }
    }
}

@Composable
private fun EmptyFavourites(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "No saved recipes yet.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Generate a recipe and tap Save to add it here.",
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun FavouritesListLayoutPopulatedPreview(
    @PreviewParameter(ThemeProvider::class) darkTheme: Boolean,
) {
    AppTheme(darkTheme) {
        FavouritesListLayout(
            favourites =
                listOf(
                    FavouriteListItem(
                        contentHash = "hash-pancakes",
                        title = "Fluffy Pancakes",
                        servings = 2,
                    ),
                    FavouriteListItem(
                        contentHash = "hash-curry",
                        title = "Vegetable Curry",
                        servings = 4,
                    ),
                ),
            onRecipeSelected = {},
            onRemoveFavourite = {},
        )
    }
}

@Preview
@Composable
private fun FavouritesListLayoutEmptyPreview(
    @PreviewParameter(ThemeProvider::class) darkTheme: Boolean,
) {
    AppTheme(darkTheme) {
        FavouritesListLayout(
            favourites = emptyList(),
            onRecipeSelected = {},
            onRemoveFavourite = {},
        )
    }
}
