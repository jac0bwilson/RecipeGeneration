package uk.jacobw.recipe.core.ui.theme.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class ThemeProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}
