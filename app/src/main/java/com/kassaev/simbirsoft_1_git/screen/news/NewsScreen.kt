package com.kassaev.simbirsoft_1_git.screen.news

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.navigation.Router

@Composable
fun NewsScreen() {
    val navController = LocalNavController.current
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