package com.kassaev.simbirsoft_1_git.screen.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@Composable
fun SearchScreen(setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit) {
    LaunchedEffect(Unit) {
        setTopAppBar{
            GetTopAppBar(
                title = R.string.search,
            )
        }
    }

    Text("Search")
}