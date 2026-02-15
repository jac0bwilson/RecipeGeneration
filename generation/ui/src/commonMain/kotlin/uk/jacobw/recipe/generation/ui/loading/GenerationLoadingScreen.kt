package uk.jacobw.recipe.generation.ui.loading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GenerationLoadingScreen(
    onRecipeGenerated: (String) -> Unit,
    viewModel: GenerationLoadingViewModel = koinViewModel(),
) {
    val generatedRecipeId by viewModel.generatedRecipeId.collectAsStateWithLifecycle()

    LaunchedEffect(generatedRecipeId) {
        generatedRecipeId?.let(onRecipeGenerated)
    }

    GenerationLoadingLayout()
}
