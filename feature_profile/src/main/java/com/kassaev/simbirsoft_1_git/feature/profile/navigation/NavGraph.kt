package com.kassaev.simbirsoft_1_git.feature.profile.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.profile.screen.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.profileNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    composable<Router.Profile> {
        ProfileScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior
        )
    }
}