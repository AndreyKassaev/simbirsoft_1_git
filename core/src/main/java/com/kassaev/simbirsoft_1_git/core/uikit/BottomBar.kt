package com.kassaev.simbirsoft_1_git.core.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.WarmGrey

data class BottomBarItem<T : Any>(
    @androidx.annotation.DrawableRes val icon: Int,
    @androidx.annotation.StringRes val title: Int,
    val route: T,
    val badgeCount: Int? = null
)

@Composable
fun BottomBarItemView(
    icon: Int,
    title: Int,
    modifier: Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    badgeCount: Int? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            }
            .then(modifier)
    ) {
        if (badgeCount != null) {
            BadgedBox(
                badge = {
                    if (badgeCount > 0) {
                        Badge(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ) {
                            Text("$badgeCount")
                        }
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = if (isSelected) Leaf else Color.Unspecified
                    )
                    Text(
                        text = stringResource(title),
                        fontSize = 10.sp,
                        color = if (isSelected) Leaf else WarmGrey
                    )
                }
            }
        } else {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = if (isSelected) Leaf else Color.Unspecified
            )
            Text(
                text = stringResource(title),
                fontSize = 10.sp,
                color = if (isSelected) Leaf else WarmGrey
            )
        }
    }
}

@Composable
fun BottomBar(
    unWatchedNewsCount: Int,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isEventDetail = currentDestination?.hierarchy?.any { NavDestination ->
        NavDestination.hasRoute<Router.EventDetail>()
    } == true
    val isAuthorization = currentDestination?.hierarchy?.any { NavDestination ->
        NavDestination.hasRoute<Router.Authorization>()
    } == true
    val bottomItemList = listOf(
        BottomBarItem(
            icon = R.drawable.list,
            title = R.string.news,
            route = Router.News,
            badgeCount = unWatchedNewsCount
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
    if (isEventDetail || isAuthorization) {
        {}
    } else {
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
                                    popUpTo(bottomItem.route) {
                                    inclusive = true
                                }
                            }
                        },
                        badgeCount = bottomItem.badgeCount
                    )
                }
            }
        }
    }
}