package com.kassaev.simbirsoft_1_git.screen.help

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.LightGreyTwo
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.Category
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.capitalizeFirstLetter
import org.koin.androidx.compose.koinViewModel

private const val GRID_SIZE = 2

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    viewModel: HelpViewModel = koinViewModel(),
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val state = viewModel.getStateFlow().collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = stringResource(R.string.help),
                scrollBehavior = scrollBehavior,
            )
        }
    }

    when(state) {
        is HelpScreenState.Success -> {
            HelpScreenSuccess(
                categoryMap = state.data
            )
        }
        is HelpScreenState.Failure -> {
            HelpScreenFailure()
        }
        is HelpScreenState.Loading -> {
            HelpScreenLoading()
        }
    }
}

@Composable
fun HelpScreenLoading() {
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

@Composable
fun HelpScreenFailure() {
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HelpScreenSuccess(
    categoryMap: Map<String, Category>,
) {
    var gridHeight by remember {
        mutableStateOf(0.dp)
    }
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 17.dp),
            text = stringResource(R.string.select_help_category),
            color = CharcoalGrey,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(categoryMap.values.chunked(2)) { chunkedCategoryList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chunkedCategoryList.forEach { category ->
                        BoxWithConstraints(
                            modifier = Modifier
                                .weight(1F)
                                .height(gridHeight)
                                .background(LightGreyTwo),
                        ) {
                            if (gridHeight == 0.dp) {
                                gridHeight = maxWidth
                            }
                            AsyncImage(
                                model = category.image,
                                contentDescription = category.name,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                            Text(
                                text = category.name.capitalizeFirstLetter(),
                                color = Leaf,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 16.dp)
                            )
                        }
                    }
                    if (chunkedCategoryList.size < GRID_SIZE) {
                        Spacer(modifier = Modifier.weight(1F))
                    }
                }
            }
        }
    }
}