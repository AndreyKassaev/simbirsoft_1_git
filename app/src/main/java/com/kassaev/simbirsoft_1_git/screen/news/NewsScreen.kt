package com.kassaev.simbirsoft_1_git.screen.news

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.navigation.Router
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit) {
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        setTopAppBar{
            GetTopAppBar(
                title = R.string.news,
            )
        }
    }
    Column {
        Text("News")
        Button(
            onClick = {
                navController.navigate(Router.Profile)
            }
        ) {
            Text("To profile")
        }
    }
}