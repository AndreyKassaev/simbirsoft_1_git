package com.kassaev.simbirsoft_1_git.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.UiKit.EventCard
import com.kassaev.simbirsoft_1_git.UiKit.NpoRow
import com.kassaev.simbirsoft_1_git.UiKit.SearchField
import com.kassaev.simbirsoft_1_git.UiKit.SearchResultMeta
import com.kassaev.simbirsoft_1_git.UiKit.SearchTabRow
import com.kassaev.simbirsoft_1_git.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.Npo
import com.kassaev.simbirsoft_1_git.util.SearchTabItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: SearchViewModel = koinViewModel()
) {
    val tabList = listOf(
        SearchTabItem(
            title = R.string.by_event,
        ),
        SearchTabItem(
            title = R.string.by_npo
        ),
    )
    var (selectedTabIndex, setSelectedTabIndex) = remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabList.size
    }
    val searchValue by viewModel.getSearchValueFlow().collectAsStateWithLifecycle()
    val eventList by viewModel.getEventListFlow().collectAsStateWithLifecycle()
    val npoList by viewModel.getNpoListFlow().collectAsStateWithLifecycle()
    val keywordList by viewModel.getKeywordList().collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = stringResource(R.string.search),
                scrollBehavior = scrollBehavior,
            )
        }
    }
    LaunchedEffect(pagerState.targetPage) {
        setSelectedTabIndex(pagerState.targetPage)
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchTabRow(
            tabList = tabList, 
            selectedTabIndex = selectedTabIndex, 
            setSelectedTabIndex = setSelectedTabIndex
        )
        HorizontalDivider(
            color = LightGrey
        )
        SearchField(
            selectedTabIndex = selectedTabIndex,
            value = searchValue,
            setValue = viewModel::setSearchValue
        )
        SearchResultMeta(
            keywordList = keywordList,
            amount = when(selectedTabIndex) {
                0 -> "${eventList.size} " + stringResource(R.string.event)
                1 -> "${npoList.size} " + stringResource(R.string.npo)
                else -> ""
            }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrey),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> {
                    EventList(
                        eventList = eventList
                    )
                }
                1 -> {
                    NpoList(
                        npoList = npoList
                    )
                }
            }
        }
    }
}


@Composable
fun EventList(eventList: List<Event>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(eventList) { event ->
            EventCard(event)
        }
    }
}

@Composable
fun NpoList(npoList: List<Npo>) {
    LazyColumn {
        npoList.forEach { npo ->
            item {
                NpoRow(npo.title)
                HorizontalDivider(color = DividerGrey)
            }
        }
    }
}

