package uk.jacobw.recipe.generation.ui.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class InputViewModel : ViewModel() {
    val inputState = TextFieldState()
}
