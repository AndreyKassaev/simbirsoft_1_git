package com.kassaev.simbirsoft_1_git.screen.history

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LaunchedEffect(Unit) {
        setTopAppBar{
            GetTopAppBar(
                title = stringResource(R.string.history),
                scrollBehavior = scrollBehavior,
            )
        }
    }

    Text("History")
}