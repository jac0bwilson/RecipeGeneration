package uk.jacobw.recipe.generation.ui.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun InputScreen(
    submitPrompt: (String) -> Unit,
    viewModel: InputViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.prompt) {
        snapshotFlow { uiState.prompt.text.toString() }
            .collectLatest {
                viewModel.validateInput(it)
            }
    }

    InputLayout(
        inputState = uiState.prompt,
        submitButtonEnabled = uiState.isPromptValid,
        onSubmitInput = {
            submitPrompt(uiState.prompt.text.toString())
        },
    )
}
