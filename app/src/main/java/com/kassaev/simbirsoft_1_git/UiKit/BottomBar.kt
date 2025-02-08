package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.model.BottomBarItem
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.navigation.Router

@Composable
fun BottomBar() {

    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomItemList = listOf(
        BottomBarItem(
            icon = R.drawable.list,
            title = R.string.news,
            route = Router.News,
        ),
        BottomBarItem(
            icon = R.drawable.search,
            title = R.string.search,
            route = Router.Search,
        ),
        BottomBarItem(
            icon = R.drawable.favorite,
            title = R.string.help,
            route = Router.Help,
        ),
        BottomBarItem(
            icon = R.drawable.history,
            title = R.string.history,
            route = Router.History,
        ),
        BottomBarItem(
            icon = R.drawable.account_circle,
            title = R.string.profile,
            route = Router.Profile,
        ),
    )
    Column {
        HorizontalDivider(modifier = Modifier.height(1.dp))
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomItemList.forEachIndexed { index, bottomItem ->
                val isCurrent = currentDestination?.hierarchy?.any { NavDestination ->
                    NavDestination.hasRoute(bottomItem.route::class)
                } == true
                BottomBarItemView(
                    icon = bottomItem.icon,
                    title = bottomItem.title,
                    modifier = Modifier.weight(1F),
                    isSelected = isCurrent,
                    onClick = {
                        navController.navigate(bottomItem.route) {
                            launchSingleTop = true
                            popUpTo(bottomItem.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
