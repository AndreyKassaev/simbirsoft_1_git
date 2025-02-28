package com.kassaev.simbirsoft_1_git.feature.auth.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.auth.screen.AuthorizationScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController
) {
    composable<Router.Authorization> {
        AuthorizationScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior,
            navController = navController
        )
    }
}