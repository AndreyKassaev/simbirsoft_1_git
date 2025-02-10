package com.kassaev.simbirsoft_1_git.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kassaev.simbirsoft_1_git.UiKit.BottomBar
import com.kassaev.simbirsoft_1_git.UiKit.FAB
import com.kassaev.simbirsoft_1_git.screen.help.HelpScreen
import com.kassaev.simbirsoft_1_git.screen.history.HistoryScreen
import com.kassaev.simbirsoft_1_git.screen.news.NewsScreen
import com.kassaev.simbirsoft_1_git.screen.profile.ProfileScreen
import com.kassaev.simbirsoft_1_git.screen.search.SearchScreen

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController found!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {

    val navController = rememberNavController()
    var (topAppBar, setTopAppBar) = remember {
        mutableStateOf<@Composable () -> Unit>({})
    }
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                topAppBar()
            },
            bottomBar = {
                BottomBar()
            },
            floatingActionButton = {
                FAB()
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Router.Profile
            ) {
                composable<Router.News> {
                    NewsScreen(setTopAppBar = setTopAppBar)
                }
                composable<Router.Search> {
                    SearchScreen(setTopAppBar = setTopAppBar)
                }
                composable<Router.Help> {
                    HelpScreen(setTopAppBar = setTopAppBar)
                }
                composable<Router.History> {
                    HistoryScreen(setTopAppBar = setTopAppBar)
                }
                composable<Router.Profile> {
                    ProfileScreen(setTopAppBar = setTopAppBar)
                }
            }
        }
    }
}