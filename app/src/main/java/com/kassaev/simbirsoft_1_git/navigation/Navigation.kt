package com.kassaev.simbirsoft_1_git.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kassaev.simbirsoft_1_git.core.uikit.BottomBar
import com.kassaev.simbirsoft_1_git.core.uikit.FAB
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.auth.navigation.authNavGraph
import com.kassaev.simbirsoft_1_git.feature.event.navigation.eventNavGraph
import com.kassaev.simbirsoft_1_git.feature.help.navigation.helpNavGraph
import com.kassaev.simbirsoft_1_git.feature.history.navigation.historyNavGraph
import com.kassaev.simbirsoft_1_git.feature.news.navigation.newsNavGraph
import com.kassaev.simbirsoft_1_git.feature.news.screen.news.NewsViewModel
import com.kassaev.simbirsoft_1_git.feature.profile.navigation.profileNavGraph
import com.kassaev.simbirsoft_1_git.feature.search.navigation.searchNavGraph

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController found!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController, eventId: String?) {

    val (topAppBar, setTopAppBar) = remember {
        mutableStateOf<@Composable () -> Unit>({})
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val newsScreenViewModel: NewsViewModel = hiltViewModel()
    val unWatchedNewsCount by newsScreenViewModel.getUnWatchedNewsCountFlow().collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                topAppBar()
            },
            bottomBar = {
                BottomBar(
                    unWatchedNewsCount = unWatchedNewsCount,
                    navController = navController
                )
            },
            floatingActionButton = {
                FAB(
                    navController = navController
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Router.News
            ) {
                newsNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                    viewModel = newsScreenViewModel,
                    navController = navController,
                )
                searchNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                    navController = navController,
                )
                helpNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                    navController = navController,
                )
                historyNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                )
                profileNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                )
                eventNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                    navController = navController
                )
                authNavGraph(
                    setTopAppBar = setTopAppBar,
                    scrollBehavior = scrollBehavior,
                    navController = navController
                )
            }
            LaunchedEffect(Unit) {
                eventId?.let {
                    navController.navigate("event_detail/$eventId")
                }
            }
        }
    }
}