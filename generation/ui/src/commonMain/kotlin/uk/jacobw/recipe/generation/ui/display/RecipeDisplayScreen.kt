package uk.jacobw.recipe.generation.ui.display

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeDisplayScreen(
    onNavigationIconClick: () -> Unit,
    viewModel: RecipeDisplayViewModel = koinViewModel(),
) {
    when (val recipe = viewModel.recipe.collectAsStateWithLifecycle().value) {
        null -> Text("Generating recipe...")
        else ->
            RecipeDisplayLayout(
                recipe = recipe,
                onNavigationIconClick = onNavigationIconClick,
            )
    }
}
