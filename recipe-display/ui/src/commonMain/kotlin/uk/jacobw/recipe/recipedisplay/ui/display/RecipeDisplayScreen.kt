package uk.jacobw.recipe.recipedisplay.ui.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider
import uk.jacobw.recipe.recipedisplay.ui.toUiModel

@Composable
fun RecipeDisplayScreen(
    onBackClick: () -> Unit,
    viewModel: RecipeDisplayViewModel = koinViewModel(),
) {
    val recipe by viewModel.recipe.collectAsState()
    val isFavourite by viewModel.isFavourite.collectAsState()
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
        Text("Recipe not found")
        Text("Tap Back to return")
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
