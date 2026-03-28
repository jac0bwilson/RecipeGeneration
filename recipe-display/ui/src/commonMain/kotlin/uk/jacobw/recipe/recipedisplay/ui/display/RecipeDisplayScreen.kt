package uk.jacobw.recipe.recipedisplay.ui.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import recipegeneration.recipe_display.ui.generated.resources.Res
import recipegeneration.recipe_display.ui.generated.resources.recipe_display_not_found_hint
import recipegeneration.recipe_display.ui.generated.resources.recipe_display_not_found_title
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider
import uk.jacobw.recipe.recipedisplay.ui.toUiModel

@Composable
fun RecipeDisplayScreen(
    onBackClick: () -> Unit,
    viewModel: RecipeDisplayViewModel = koinViewModel(),
) {
    val recipe by viewModel.recipe.collectAsStateWithLifecycle()
    val isFavourite by viewModel.isFavourite.collectAsStateWithLifecycle()
    val closesAfterFavouriteToggle = viewModel.closesAfterFavouriteToggle

    when (val generatedRecipe = recipe) {
        null -> RecipeNotFoundLayout()
        else ->
            RecipeDisplayLayout(
                recipe = generatedRecipe.toUiModel(),
                isFavourite = isFavourite,
                onBackClick = onBackClick,
                onFavouriteClick = {
                    viewModel.onFavouriteClicked()

                    if (closesAfterFavouriteToggle) {
                        onBackClick()
                    }
                },
            )
    }
}

@Composable
private fun RecipeNotFoundLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(Res.string.recipe_display_not_found_title))
        Text(stringResource(Res.string.recipe_display_not_found_hint))
    }
}

@Preview
@Composable
private fun RecipeNotFoundLayoutPreview(
    @PreviewParameter(ThemeProvider::class) darkTheme: Boolean,
) {
    AppTheme(darkTheme) {
        RecipeNotFoundLayout()
    }
}
