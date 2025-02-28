package com.kassaev.simbirsoft_1_git.feature.help.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.help.screen.HelpScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.helpNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController
) {
    composable<Router.Help> {
        HelpScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior,
            navController = navController
        )
    }
}