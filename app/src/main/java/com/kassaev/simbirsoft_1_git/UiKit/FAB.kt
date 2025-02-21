package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.navigation.Router
import com.kassaev.simbirsoft_1_git.navigation.Router.Authorization
import com.kassaev.simbirsoft_1_git.navigation.Router.EventDetail
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.Melon

@Composable
fun FAB() {

    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isCurrent = currentDestination?.hierarchy?.any { NavDestination ->
        NavDestination.hasRoute(Router.Help::class)
    } == true
    val isEventDetail = currentDestination?.hierarchy?.any { NavDestination ->
        NavDestination.hasRoute<EventDetail>()
    } == true
    val isAuthorization = currentDestination?.hierarchy?.any { NavDestination ->
        NavDestination.hasRoute<Authorization>()
    } == true
    if (isEventDetail || isAuthorization) {
        {}
    } else {
        SmallFloatingActionButton(
            modifier = Modifier
                .offset(y = (52).dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(2.dp),
            onClick = {
                navController.navigate(Router.Help) {
                    launchSingleTop = true
                    popUpTo(Router.Help) { inclusive = true }
                }
            },
            shape = CircleShape,
            containerColor = if (isCurrent) Leaf else Melon
        ) {
            Icon(
                painter = painterResource(R.drawable.favorite),
                contentDescription = "Add",
                tint = Color.Unspecified
            )
        }
    }
}