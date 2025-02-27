package com.kassaev.simbirsoft_1_git.feature.news.screen.news

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.uikit.EventCard
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.Melon
import com.kassaev.simbirsoft_1_git.core.ui.theme.White
import com.kassaev.simbirsoft_1_git.core.util.Event
import com.kassaev.simbirsoft_1_git.core.util.FilterSwitchState
import com.kassaev.simbirsoft_1_git.core.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val state = viewModel.getStateFlow().collectAsStateWithLifecycle().value
    val filterState by viewModel.getFilterSwitchStateFlow().collectAsStateWithLifecycle()
    val isServiceStarted by viewModel.getIsServiceStarted().collectAsStateWithLifecycle()
    var isFilterOpen by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isServiceStarted) {
        if (!isServiceStarted) {
            viewModel.getNewsList()
        }
    }
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = if (isFilterOpen) stringResource(R.string.filter) else stringResource(R.string.news),
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            isFilterOpen = !isFilterOpen
                        }
                    ) {
                        Icon(
                            painter = painterResource(if (isFilterOpen) R.drawable.check_circle else R.drawable.filter),
                            contentDescription = stringResource(R.string.filter)
                        )
                    }
                },
                navigationIcon = if (isFilterOpen) {
                    {
                        IconButton(
                            onClick = {
                                isFilterOpen = !isFilterOpen
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_back),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                } else {
                    {}
                }
            )
        }
    }
    when(state) {
        is NewsScreenState.Success -> {
            NewsScreenSuccess(
                newsList = state.data,
                setFilterSwitchMoneyState = viewModel::setFilterSwitchMoneyState,
                setFilterSwitchStuffState = viewModel::setFilterSwitchStuffState,
                filterState = filterState,
                isFilterOpen = isFilterOpen,
                setIsWatched = viewModel::setIsWatched,
                navController = navController,
            )
        }
        is NewsScreenState.Failure -> {
            NewsScreenFailure()
        }
        is NewsScreenState.Loading -> {
            NewsScreenLoading()
        }
    }

}

@Composable
fun NewsScreenSuccess(
    newsList: List<Event>,
    setFilterSwitchStuffState: (Boolean) -> Unit,
    setFilterSwitchMoneyState: (Boolean) -> Unit,
    filterState: FilterSwitchState,
    isFilterOpen: Boolean,
    setIsWatched: (String) -> Unit,
    navController: NavController,
) {
    if (isFilterOpen) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrey)
        ) {
            Text(
                text = stringResource(R.string.how_to_help),
                fontSize = 17.sp,
                color = CharcoalGrey,
                modifier = Modifier
                    .padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .background(White)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.money),
                    fontSize = 17.sp
                )
                Switch(
                    checked = filterState.money,
                    onCheckedChange = {
                        setFilterSwitchMoneyState(it)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Leaf,
                        uncheckedTrackColor = Melon,
                        uncheckedThumbColor = White,
                    )
                )
            }
            HorizontalDivider(color = DividerGrey)
            Row(
                modifier = Modifier
                    .background(White)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.stuff),
                    fontSize = 17.sp
                )
                Switch(
                    checked = filterState.stuff,
                    onCheckedChange = {
                        setFilterSwitchStuffState(it)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Leaf,
                        uncheckedTrackColor = Melon,
                        uncheckedThumbColor = White,
                    )
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {} //Space between topAppBar and first item
            items(newsList) { news ->
                EventCard(
                    event = news,
                    setIsWatched = setIsWatched,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun NewsScreenFailure() {
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
fun NewsScreenLoading() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotate"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .rotate(rotation),
            model = "https://kassaev.com/media/pepe_sitting.gif",
            contentDescription = null,
        )
    }
}