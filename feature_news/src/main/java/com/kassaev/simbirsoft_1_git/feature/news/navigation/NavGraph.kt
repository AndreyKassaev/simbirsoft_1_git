package com.kassaev.simbirsoft_1_git.feature.news.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.news.screen.news.NewsScreen
import com.kassaev.simbirsoft_1_git.feature.news.screen.news.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.newsNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: NewsViewModel,
    navController: NavController,
) {
    composable<Router.News> {
        NewsScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior,
            navController = navController,
            viewModel = viewModel,
        )
    }
}