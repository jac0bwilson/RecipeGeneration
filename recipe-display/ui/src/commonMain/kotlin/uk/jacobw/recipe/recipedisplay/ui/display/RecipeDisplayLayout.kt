package uk.jacobw.recipe.recipedisplay.ui.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import uk.jacobw.recipe.core.ui.component.Title
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider
import uk.jacobw.recipe.recipedisplay.ui.DisplayDifficulty
import uk.jacobw.recipe.recipedisplay.ui.DisplayDuration
import uk.jacobw.recipe.recipedisplay.ui.DisplayIngredient
import uk.jacobw.recipe.recipedisplay.ui.DisplayInstruction
import uk.jacobw.recipe.recipedisplay.ui.DisplayRecipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDisplayLayout(
    recipe: DisplayRecipe,
    isFavourite: Boolean,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Text("Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = onFavouriteClick,
                    ) {
                        Text(
                            text = if (isFavourite) "Saved" else "Save",
                        )
                    }
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { internalPadding ->
        Column(
            modifier =
                Modifier
                    .padding(internalPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            HeadingSection(recipe.title)

            InfoSummarySection(
                duration = recipe.estimatedDuration,
                difficulty = recipe.difficulty,
                servings = recipe.servings,
            )

            IngredientsSection(recipe.ingredients)

            InstructionsSection(recipe.instructions)

            recipe.comment?.let {
                CommentSection(it)
            }
        }
    }
}

@Composable
private fun HeadingSection(title: String) {
    Title(
        text =
            buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(title)
                }
            },
    )
}

@Composable
private fun InfoSummarySection(
    duration: DisplayDuration,
    difficulty: DisplayDifficulty,
    servings: Int,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InfoChip(
            content = duration.toReadableString(),
        )

        InfoChip(
            content = difficulty.name.lowercase().replaceFirstChar { it.uppercase() },
        )

        InfoChip(
            content = "$servings servings",
        )
    }
}

@Composable
private fun IngredientsSection(ingredients: List<DisplayIngredient>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = "${ingredients.size} items",
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
            )
        }

        HorizontalDivider()

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ingredients.forEach { ingredient ->
                InfoChip(
                    content = ingredient.quantity + " " + ingredient.name,
                )
            }
        }
    }
}

@Composable
private fun InstructionsSection(instructions: List<DisplayInstruction>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleLarge,
        )

        HorizontalDivider()

        instructions.forEachIndexed { index, instruction ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                ) {
                    val circleColor = MaterialTheme.colorScheme.primary
                    Box(
                        modifier =
                            Modifier.drawBehind {
                                drawCircle(
                                    color = circleColor,
                                    radius = size.maxDimension / 2.0f,
                                )
                            },
                    ) {
                        Text(
                            text = "${index + 1}.",
                            style =
                                MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                ),
                            modifier = Modifier.padding(4.dp),
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = instruction.title,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Text(
                            text = instruction.detail,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentSection(comment: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Chef's Comment",
            )
            Text(
                text = comment,
            )
        }
    }
}

@Composable
private fun InfoChip(content: String) {
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
    )
}

private fun DisplayDuration.toReadableString(): String {
    val hoursPart = if (hours > 0) "$hours h " else ""
    val minutesPart = if (minutes > 0) "$minutes min" else ""
    return (hoursPart + minutesPart).trim()
}

@Preview
@Composable
private fun RecipeDisplayLayoutPreview(
    @PreviewParameter(ThemeProvider::class) darkTheme: Boolean,
) {
    AppTheme(darkTheme) {
        RecipeDisplayLayout(
            recipe =
                DisplayRecipe(
                    title = "Pancakes",
                    estimatedDuration =
                        DisplayDuration(
                            hours = 0,
                            minutes = 20,
                        ),
                    difficulty = DisplayDifficulty.EASY,
                    servings = 2,
                    ingredients =
                        listOf(
                            DisplayIngredient(
                                name = "Flour",
                                quantity = "200g",
                                commonAllergen = true,
                            ),
                            DisplayIngredient(
                                name = "Milk",
                                quantity = "300ml",
                                commonAllergen = true,
                            ),
                            DisplayIngredient(
                                name = "Eggs",
                                quantity = "2",
                                commonAllergen = false,
                            ),
                        ),
                    instructions =
                        listOf(
                            DisplayInstruction(
                                title = "Mix Ingredients",
                                detail = "In a large bowl, whisk together the flour, milk, and eggs until smooth.",
                            ),
                            DisplayInstruction(
                                title = "Cook Pancakes",
                                detail =
                                    """
                                    Heat a non-stick pan over medium heat. Pour 1/4 cup of batter for each pancake.
                                    Cook and then flip.
                                    """.trimIndent(),
                            ),
                        ),
                    comment = "A simple pancake recipe",
                ),
            isFavourite = false,
            onBackClick = {},
            onFavouriteClick = {},
        )
    }
}
