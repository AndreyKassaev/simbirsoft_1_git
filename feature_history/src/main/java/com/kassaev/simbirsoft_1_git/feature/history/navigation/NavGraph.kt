package com.kassaev.simbirsoft_1_git.feature.history.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.feature.history.ui.screen.HistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.historyNavGraph(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    composable<Router.History> {
        HistoryScreen(
            setTopAppBar = setTopAppBar,
            scrollBehavior = scrollBehavior
        )
    }
}