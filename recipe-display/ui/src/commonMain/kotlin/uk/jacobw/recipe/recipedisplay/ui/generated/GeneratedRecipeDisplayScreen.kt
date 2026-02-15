package uk.jacobw.recipe.recipedisplay.ui.generated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import org.koin.compose.viewmodel.koinViewModel
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplayLayout

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
