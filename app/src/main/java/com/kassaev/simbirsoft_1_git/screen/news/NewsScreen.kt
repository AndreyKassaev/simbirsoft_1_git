package com.kassaev.simbirsoft_1_git.screen.news

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    LaunchedEffect(Unit) {
        setTopAppBar{
            GetTopAppBar(
                title = R.string.news,
                scrollBehavior = scrollBehavior
            )
        }
    }
}