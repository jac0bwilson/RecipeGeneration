package uk.jacobw.recipe.generation.ui.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import recipegeneration.generation.ui.generated.resources.Res
import recipegeneration.generation.ui.generated.resources.arrow_back_icon
import recipegeneration.generation.ui.generated.resources.brain_icon
import recipegeneration.generation.ui.generated.resources.check_icon
import recipegeneration.generation.ui.generated.resources.groups_icon
import recipegeneration.generation.ui.generated.resources.timer_icon
import uk.jacobw.recipe.core.ui.component.Title
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.core.ui.theme.preview.ThemeProvider
import uk.jacobw.recipe.generation.domain.model.Difficulty
import uk.jacobw.recipe.generation.domain.model.Duration
import uk.jacobw.recipe.generation.domain.model.Ingredient
import uk.jacobw.recipe.generation.domain.model.Instruction
import uk.jacobw.recipe.generation.domain.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDisplayLayout(
    recipe: Recipe,
    onNavigationIconClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconClick,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back_icon),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { internalPadding ->
        Column(
            modifier =
                Modifier
                    .windowInsetsPadding(WindowInsets.systemBars)
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
    duration: Duration,
    difficulty: Difficulty,
    servings: Int,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InfoChip(
            content = duration.toReadableString(),
            icon = painterResource(Res.drawable.timer_icon),
        )

        InfoChip(
            content = difficulty.name.lowercase().replaceFirstChar { it.uppercase() },
            icon = painterResource(Res.drawable.brain_icon),
        )

        InfoChip(
            content = "$servings servings",
            icon = painterResource(Res.drawable.groups_icon),
        )
    }
}

@Composable
private fun IngredientsSection(ingredients: List<Ingredient>) {
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
                    icon = painterResource(Res.drawable.check_icon),
                )
            }
        }
    }
}

@Composable
private fun InstructionsSection(instructions: List<Instruction>) {
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
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = comment,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun InfoChip(
    content: String,
    icon: Painter,
) {
    AssistChip(
        onClick = {},
        label = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = null,
            )
        },
    )
}

private fun Duration.toReadableString(): String {
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
                Recipe(
                    title = "Pancakes",
                    estimatedDuration =
                        Duration(
                            hours = 0,
                            minutes = 20,
                        ),
                    difficulty = Difficulty.EASY,
                    servings = 2,
                    ingredients =
                        listOf(
                            Ingredient(
                                name = "Flour",
                                quantity = "200g",
                                commonAllergen = true,
                            ),
                            Ingredient(
                                name = "Milk",
                                quantity = "300ml",
                                commonAllergen = true,
                            ),
                            Ingredient(
                                name = "Eggs",
                                quantity = "2",
                                commonAllergen = false,
                            ),
                        ),
                    instructions =
                        listOf(
                            Instruction(
                                title = "Mix Ingredients",
                                detail = "In a large bowl, whisk together the flour, milk, and eggs until smooth.",
                            ),
                            Instruction(
                                title = "Cook Pancakes",
                                detail =
                                    """
                                    Heat a non-stick pan over medium heat. Pour 1/4 cup of batter for each pancake.
                                    Cook until bubbles form on the surface, then flip and cook until golden brown
                                    on the other side.
                                    """.trimIndent(),
                            ),
                        ),
                    comment = "A simple pancake recipe",
                ),
            onNavigationIconClick = {},
        )
    }
}
