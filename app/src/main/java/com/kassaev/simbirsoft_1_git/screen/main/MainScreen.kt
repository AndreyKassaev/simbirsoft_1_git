package com.kassaev.simbirsoft_1_git.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.navigation.Router

@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    Column {
        Text("Main Screen")
        Button(
            onClick = {
                navController.navigate(Router.ProfileScreen)
            }
        ) {
            Text("To profile")
        }
    }
}