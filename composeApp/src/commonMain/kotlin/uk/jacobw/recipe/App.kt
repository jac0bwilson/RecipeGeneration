package uk.jacobw.recipe

import androidx.compose.foundation.layout.padding
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
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.favourites.ui.FavouritesRoutes
import uk.jacobw.recipe.favourites.ui.favouritesGraph
import uk.jacobw.recipe.favourites.ui.getFavouritesUiModules
import uk.jacobw.recipe.generation.ui.GenerationRoutes
import uk.jacobw.recipe.generation.ui.generationGraph
import uk.jacobw.recipe.generation.ui.getGenerationUiModules
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplayRoutes
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
                                Text("G")
                            },
                            label = {
                                Text("Generation")
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
                                Text("F")
                            },
                            label = {
                                Text("Favourites")
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
                            navController.navigate(RecipeDisplayRoutes.Generated(recipeId))
                        },
                    )

                    favouritesGraph(
                        onRecipeSelected = { contentHash ->
                            selectedTab = RootTab.FAVOURITES
                            navController.navigate(RecipeDisplayRoutes.Favourite(contentHash))
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
