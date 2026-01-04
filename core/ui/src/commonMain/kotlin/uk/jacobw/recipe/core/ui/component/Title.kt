package uk.jacobw.recipe.core.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Title(text: String) {
    Title(
        text = AnnotatedString(text),
    )
}

@Composable
fun Title(text: AnnotatedString) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayMedium,
        fontWeight = FontWeight.Bold,
    )
}
