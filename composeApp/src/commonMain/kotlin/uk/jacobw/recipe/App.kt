package uk.jacobw.recipe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import recipegeneration.composeapp.generated.resources.Res as ComposeAppRes
import recipegeneration.composeapp.generated.resources.tab_favourites_icon_content_description
import recipegeneration.composeapp.generated.resources.tab_favourites_label
import recipegeneration.composeapp.generated.resources.tab_generation_icon_content_description
import recipegeneration.composeapp.generated.resources.tab_generation_label
import recipegeneration.core.ui.generated.resources.Res as CoreRes
import recipegeneration.core.ui.generated.resources.favourite_icon
import recipegeneration.core.ui.generated.resources.wand_stars_icon
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.favourites.ui.FavouritesRoutes
import uk.jacobw.recipe.favourites.ui.favouritesGraph
import uk.jacobw.recipe.favourites.ui.getFavouritesUiModules
import uk.jacobw.recipe.generation.ui.GenerationRoutes
import uk.jacobw.recipe.generation.ui.generationGraph
import uk.jacobw.recipe.generation.ui.getGenerationUiModules
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplayRoutes
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplaySource
import uk.jacobw.recipe.recipedisplay.ui.getRecipeDisplayUiModules
import uk.jacobw.recipe.recipedisplay.ui.recipeDisplayGraph

private enum class RootTab {
    GENERATION,
    FAVOURITES,
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(
        config =
            KoinConfiguration {
                modules(
                    AppModule().module + getGenerationUiModules() + getFavouritesUiModules() + getRecipeDisplayUiModules(),
                )
            },
    ) {
        AppTheme {
            val navController = rememberNavController()
            var selectedTab by rememberSaveable { mutableStateOf(RootTab.GENERATION) }

            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selectedTab == RootTab.GENERATION,
                            onClick = {
                                selectedTab = RootTab.GENERATION
                                navController.navigate(GenerationRoutes.Root) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(CoreRes.drawable.wand_stars_icon),
                                    contentDescription = stringResource(ComposeAppRes.string.tab_generation_icon_content_description),
                                )
                            },
                            label = {
                                Text(stringResource(ComposeAppRes.string.tab_generation_label))
                            },
                        )

                        NavigationBarItem(
                            selected = selectedTab == RootTab.FAVOURITES,
                            onClick = {
                                selectedTab = RootTab.FAVOURITES
                                navController.navigate(FavouritesRoutes.Root) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(CoreRes.drawable.favourite_icon),
                                    contentDescription = stringResource(ComposeAppRes.string.tab_favourites_icon_content_description),
                                )
                            },
                            label = {
                                Text(stringResource(ComposeAppRes.string.tab_favourites_label))
                            },
                        )
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = GenerationRoutes.Root,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    generationGraph(
                        navController = navController,
                        onGeneratedRecipeCreated = { recipeId ->
                            selectedTab = RootTab.GENERATION
                            navController.navigate(
                                RecipeDisplayRoutes.Recipe(
                                    source = RecipeDisplaySource.GENERATED,
                                    reference = recipeId,
                                ),
                            )
                        },
                    )

                    favouritesGraph(
                        onRecipeSelected = { contentHash ->
                            selectedTab = RootTab.FAVOURITES
                            navController.navigate(
                                RecipeDisplayRoutes.Recipe(
                                    source = RecipeDisplaySource.FAVOURITE,
                                    reference = contentHash,
                                ),
                            )
                        },
                    )

                    recipeDisplayGraph(
                        navController = navController,
                    )
                }
            }
        }
    }
}
