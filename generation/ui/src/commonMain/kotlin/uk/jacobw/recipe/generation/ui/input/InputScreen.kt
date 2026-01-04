package uk.jacobw.recipe.generation.ui.input

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun InputScreen(viewModel: InputViewModel = koinViewModel()) {
    InputLayout(
        inputState = viewModel.inputState,
    )
}
