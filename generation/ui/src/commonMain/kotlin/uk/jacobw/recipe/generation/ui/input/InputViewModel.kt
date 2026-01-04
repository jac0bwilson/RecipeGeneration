package uk.jacobw.recipe.generation.ui.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class InputViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InputUiState())
    val uiState = _uiState.asStateFlow()

    fun validateInput(input: String) {
        val isValid = input.isNotBlank()

        _uiState.update {
            it.copy(isPromptValid = isValid)
        }
    }
}

data class InputUiState(
    val prompt: TextFieldState = TextFieldState(),
    val isPromptValid: Boolean = false,
)
