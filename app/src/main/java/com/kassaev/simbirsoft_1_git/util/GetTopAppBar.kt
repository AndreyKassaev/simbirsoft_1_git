package com.kassaev.simbirsoft_1_git.util

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GetTopAppBar(
    @StringRes title: Int,
    actions: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(title)
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