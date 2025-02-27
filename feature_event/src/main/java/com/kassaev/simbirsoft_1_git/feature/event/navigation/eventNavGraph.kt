package com.kassaev.simbirsoft_1_git.feature.event.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.event.screen.EventDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.eventNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
) {
    composable<Router.EventDetail> {
        EventDetailScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior,
            navController = navController
        )
    }
}