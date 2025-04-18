package com.kassaev.simbirsoft_1_git.feature.search.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.search.screen.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController
) {
    composable<Router.Search> {
        SearchScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior,
            navController = navController
        )
    }
}