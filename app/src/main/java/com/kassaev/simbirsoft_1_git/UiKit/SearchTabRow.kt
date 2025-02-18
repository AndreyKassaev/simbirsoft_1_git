package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.SearchTabItem

@Composable
fun SearchTabRow(
    tabList: List<SearchTabItem>,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Leaf, shape = RoundedCornerShape(4.dp))
    ) {
        tabList.forEachIndexed { index, tabItem ->
            Text(
                modifier = Modifier
                    .clip(
                        if (index == 0) RoundedCornerShape(
                            topStart = 4.dp,
                            bottomStart = 4.dp
                        ) else RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                    )
                    .weight(1F)
                    .clickable {
                        setSelectedTabIndex(index)
                    }
                    .background(if (selectedTabIndex == index) Leaf else White)
                    .padding(vertical = 4.dp),
                text = stringResource(tabItem.title),
                color = if (selectedTabIndex == index) White else Leaf,
                textAlign = TextAlign.Center
            )
        }
    }
}
