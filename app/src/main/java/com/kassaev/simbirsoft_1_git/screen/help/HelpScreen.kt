package com.kassaev.simbirsoft_1_git.screen.help

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.LightGreyTwo
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
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
    val categoryList = viewModel.getCategoryList()
    var gridHeight by remember {
        mutableStateOf(0.dp)
    }
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = R.string.help,
                scrollBehavior = scrollBehavior,
            )
        }
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
            items(categoryList.chunked(2)) { chunkedCategoryList ->
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
                            Image(
                                painter = painterResource(category.image),
                                contentDescription = stringResource(category.title),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                            Text(
                                text = stringResource(category.title),
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