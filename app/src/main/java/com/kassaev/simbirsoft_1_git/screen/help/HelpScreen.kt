package com.kassaev.simbirsoft_1_git.screen.help

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@Composable
fun HelpScreen(setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit) {
    LaunchedEffect(Unit) {
        setTopAppBar{
            GetTopAppBar(
                title = R.string.help,
            )
        }
    }

    Text("Help")
}