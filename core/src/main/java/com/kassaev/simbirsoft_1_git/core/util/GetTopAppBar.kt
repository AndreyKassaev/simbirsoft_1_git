package com.kassaev.simbirsoft_1_git.core.util

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GetTopAppBar(
    title: String,
    actions: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title
            )
        },
        actions = {
            actions()
        },
        navigationIcon = {
            navigationIcon()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Leaf,
            titleContentColor = White,
            actionIconContentColor = White,
            navigationIconContentColor = White,
            scrolledContainerColor = Leaf,
        ),
        scrollBehavior = scrollBehavior
    )
}