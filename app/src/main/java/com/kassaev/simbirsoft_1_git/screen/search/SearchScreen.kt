package com.kassaev.simbirsoft_1_git.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.UiKit.EventCard
import com.kassaev.simbirsoft_1_git.navigation.Router
import com.kassaev.simbirsoft_1_git.ui.theme.BlueGrey
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGreyLight
import com.kassaev.simbirsoft_1_git.ui.theme.DarkSlateBlue
import com.kassaev.simbirsoft_1_git.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.ui.theme.SearchText
import com.kassaev.simbirsoft_1_git.ui.theme.SoftLight
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.SearchTabItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit) {
    val tabList = listOf(
        SearchTabItem(
            title = R.string.by_event,
        ),
        SearchTabItem(
            title = R.string.by_npo
        ),
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    var searchValue by remember {
        mutableStateOf("")
    }
    val pagerState = rememberPagerState {
        tabList.size
    }
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = R.string.search,
            )
        }
    }
    LaunchedEffect(pagerState.targetPage) {
        selectedTabIndex = pagerState.targetPage
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                            selectedTabIndex = index
                        }
                        .background(if (selectedTabIndex == index) Leaf else White)
                        .padding(vertical = 4.dp),
                    text = stringResource(tabItem.title),
                    color = if (selectedTabIndex == index) White else Leaf,
                    textAlign = TextAlign.Center
                )
            }
        }
        HorizontalDivider(color = LightGrey)
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
            textStyle = TextStyle(
                color = SearchText,
                fontSize = 14.sp
            ),
            singleLine = true,
            decorationBox = { innerText ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SoftLight, RoundedCornerShape(4.dp))
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = stringResource(R.string.search),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        tint = SearchText
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(if (searchValue.isEmpty()) 0.01F else 1F)
                    ) {
                        innerText()
                    }
                    if (searchValue.isEmpty()) {
                        Text(
                            text = stringResource(if (selectedTabIndex == 0) R.string.search_placeholder_event else R.string.search_placeholder_npo),
                            color = SearchText
                        )
                    }
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGrey)
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.keywords) + ": ",
                    fontWeight = FontWeight.SemiBold,
                    color = CharcoalGrey
                )
                Text(
                    text = LoremIpsum(3).values.joinToString(" "),
                    color = CharcoalGreyLight,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = stringResource(R.string.search_results) + ": ",
                    fontWeight = FontWeight.SemiBold,
                    color = CharcoalGrey
                )
                Text(
                    text = "${Random.nextInt(1, 100)} " + stringResource(R.string.event),
                    color = CharcoalGreyLight
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrey),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> {
                    EventList()
                }

                1 -> {
                    NpoList()
                }
            }
        }
    }
}

@Composable
fun EventList() {
    EventCard(Event.default)
}

@Composable
fun NpoList() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = LoremIpsum(17).values.joinToString(" "),
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(9F)
        )
        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .weight(1F)
        )
    }
}