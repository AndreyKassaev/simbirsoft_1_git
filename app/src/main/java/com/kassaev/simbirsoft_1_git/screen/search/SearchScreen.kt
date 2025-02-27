package com.kassaev.simbirsoft_1_git.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.UiKit.EventCard
import com.kassaev.simbirsoft_1_git.UiKit.NpoRow
import com.kassaev.simbirsoft_1_git.UiKit.SearchField
import com.kassaev.simbirsoft_1_git.UiKit.SearchResultMeta
import com.kassaev.simbirsoft_1_git.UiKit.SearchTabRow
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.Npo
import com.kassaev.simbirsoft_1_git.util.SearchTabItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: SearchViewModel = hiltViewModel()
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

    val state by viewModel.getStateFlow().collectAsStateWithLifecycle()

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
        when(state) {
            is SearchScreenState.Empty -> {
                SearchScreenEmpty()
            }
            is SearchScreenState.Failure -> {
                SearchScreenFailure()
            }
            is SearchScreenState.Init -> {
                SearchScreenInit()
            }
            is SearchScreenState.Loading -> {
                SearchScreenLoading()
            }
            is SearchScreenState.Success -> {
                SearchScreenSuccess(state as SearchScreenState.Success, selectedTabIndex, pagerState)
            }
        }
    }
}

@Composable
private fun SearchScreenSuccess(
    state: SearchScreenState.Success,
    selectedTabIndex: Int,
    pagerState: PagerState
) {
    SearchResultMeta(
        keywordList = state.data.keywordList,
        amount = when (selectedTabIndex) {
            0 -> "${state.data.eventList.size} " + stringResource(R.string.event)
            1 -> "${state.data.npoList.size} " + stringResource(R.string.npo)
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
                    eventList = state.data.eventList
                )
            }

            1 -> {
                NpoList(
                    npoList = state.data.npoList
                )
            }
        }
    }
}

@Composable
private fun SearchScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Leaf
        )
    }
}

@Composable
private fun SearchScreenInit() {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.search_advice_example))
        append(" ")
        pushStringAnnotation(tag = "master-class", annotation = "master-class")
        withStyle(style = SpanStyle(color = Leaf, textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.master_class))
        }
        pop()
        append(", ")
        pushStringAnnotation(tag = "help", annotation = "help")
        withStyle(style = SpanStyle(color = Leaf, textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.search_help))
        }
        pop()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrey)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.search_image),
                contentDescription = stringResource(R.string.search),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.search_advice),
                color = CharcoalGrey,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            ClickableText(
                text = annotatedString,
                style = TextStyle(color = CharcoalGrey, fontSize = 15.sp),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "master-class",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            println("master-class")
                        }

                    annotatedString.getStringAnnotations(tag = "help", start = offset, end = offset)
                        .firstOrNull()?.let {
                            println("help")
                        }
                }
            )
        }
    }
}

@Composable
private fun SearchScreenEmpty() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrey),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty),
            color = CharcoalGrey,
        )
    }
}

@Composable
private fun SearchScreenFailure() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            painter = painterResource(R.drawable.sad_pepe),
            contentDescription = stringResource(R.string.oops),
            contentScale = ContentScale.FillWidth,
        )
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

