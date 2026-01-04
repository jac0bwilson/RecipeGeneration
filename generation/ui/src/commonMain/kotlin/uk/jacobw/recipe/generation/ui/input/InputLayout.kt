package uk.jacobw.recipe.generation.ui.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import recipegeneration.generation.ui.generated.resources.Res
import recipegeneration.generation.ui.generated.resources.generate_button
import recipegeneration.generation.ui.generated.resources.input_placeholder
import recipegeneration.generation.ui.generated.resources.title_part_one
import recipegeneration.generation.ui.generated.resources.title_part_two
import recipegeneration.generation.ui.generated.resources.wand_stars_icon
import uk.jacobw.recipe.core.ui.component.Title
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider

@Composable
internal fun InputLayout(
    inputState: TextFieldState,
    submitButtonEnabled: Boolean,
    onSubmitInput: () -> Unit,
) {
    Scaffold { internalPadding ->
        Column(
            modifier =
                Modifier
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(internalPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            HeadingSection()
            InputSection(inputState)

            Spacer(Modifier.weight(1f))

            SubmitSection(
                enabled = submitButtonEnabled,
                onClick = onSubmitInput,
            )
        }
    }
}

@Composable
private fun HeadingSection() {
    Title(
        text =
            buildAnnotatedString {
                append(stringResource(Res.string.title_part_one))

                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(Res.string.title_part_two))
                }
            },
    )
}

@Composable
private fun InputSection(inputState: TextFieldState) {
    TextField(
        state = inputState,
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 128.dp),
        placeholder = {
            Text(stringResource(Res.string.input_placeholder))
        },
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
private fun SubmitSection(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.wand_stars_icon),
                contentDescription = null,
            )
            Text(
                text = stringResource(Res.string.generate_button),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun InputLayoutPreview(
    @PreviewParameter(ThemeProvider::class) darkTheme: Boolean,
) {
    AppTheme(darkTheme) {
        InputLayout(
            inputState = TextFieldState(),
            submitButtonEnabled = true,
            onSubmitInput = {},
        )
    }
}
