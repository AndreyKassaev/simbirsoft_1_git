package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.WarmGrey

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