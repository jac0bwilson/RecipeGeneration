package uk.jacobw.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import uk.jacobw.recipe.core.ui.theme.AppTheme
import uk.jacobw.recipe.favourites.ui.detail.FavouriteRecipeDisplayViewModel
import uk.jacobw.recipe.favourites.ui.FavouritesRoutes
import uk.jacobw.recipe.favourites.ui.favouritesGraph
import uk.jacobw.recipe.favourites.ui.getFavouritesUiModules
import uk.jacobw.recipe.generation.ui.GenerationRoutes
import uk.jacobw.recipe.generation.ui.display.GenerationRecipeLoadingLayout
import uk.jacobw.recipe.generation.ui.generationGraph
import uk.jacobw.recipe.generation.ui.getGenerationUiModules
import uk.jacobw.recipe.recipedisplay.ui.RecipeDisplayLayout

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
                modules(AppModule().module + getGenerationUiModules() + getFavouritesUiModules())
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
                        generatedRecipeScreen = { onBackClick ->
                            selectedTab = RootTab.GENERATION
                            GeneratedRecipeDisplayScreen(onBackClick = onBackClick)
                        },
                    )

                    favouritesGraph(
                        navController = navController,
                        favouriteDetailScreen = { onBackClick ->
                            selectedTab = RootTab.FAVOURITES
                            FavouriteRecipeDisplayScreen(onBackClick = onBackClick)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun GeneratedRecipeDisplayScreen(
    onBackClick: () -> Unit,
    viewModel: GeneratedRecipeDisplayViewModel = koinViewModel(),
) {
    val recipe by viewModel.recipe.collectAsStateWithLifecycle()
    val isFavourite by viewModel.isFavourite.collectAsStateWithLifecycle()

    when (val generatedRecipe = recipe) {
        null -> GenerationRecipeLoadingLayout()
        else ->
            RecipeDisplayLayout(
                recipe = generatedRecipe.toDisplayRecipe(),
                isFavourite = isFavourite,
                onBackClick = onBackClick,
                onFavouriteClick = viewModel::onFavouriteClicked,
            )
    }
}

@Composable
private fun FavouriteRecipeDisplayScreen(
    onBackClick: () -> Unit,
    viewModel: FavouriteRecipeDisplayViewModel = koinViewModel(),
) {
    val savedRecipe by viewModel.savedRecipe.collectAsStateWithLifecycle()

    when (val favourite = savedRecipe) {
        null ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Recipe not found")
                Text("Tap Back to return")
            }

        else ->
            RecipeDisplayLayout(
                recipe = favourite.recipe.toDisplayRecipe(),
                isFavourite = true,
                onBackClick = onBackClick,
                onFavouriteClick = {
                    viewModel.removeFavourite()
                    onBackClick()
                },
            )
    }
}
