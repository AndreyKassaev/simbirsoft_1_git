package com.kassaev.simbirsoft_1_git.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kassaev.simbirsoft_1_git.screen.main.MainScreen
import com.kassaev.simbirsoft_1_git.screen.profile.ProfileScreen

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController found!")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {},
            bottomBar = {},
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Router.MainScreen
            ) {
                composable<Router.MainScreen> {
                    MainScreen()
                }
                composable<Router.ProfileScreen> {
                    ProfileScreen()
                }
            }
        }
    }
}