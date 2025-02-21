package com.kassaev.simbirsoft_1_git.screen.event_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.ui.theme.BlueGrey
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.timestamFormatter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: EventDetailViewModel = koinViewModel()
) {
    val event by viewModel.getEventFlow().collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    event?.let { event ->
        LaunchedEffect(Unit) {
            setTopAppBar {
                GetTopAppBar(
                    title = event.title,
                    scrollBehavior = scrollBehavior,
                    actions = {
                        IconButton(
                            onClick = {
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.share),
                                contentDescription = stringResource(R.string.share)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_back),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {  }
                item {
                    Text(
                        text = event.title,
                        fontSize = 21.sp,
                        color = BlueGrey
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.calendar),
                            contentDescription = stringResource(R.string.calendar),
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(14.dp),
                            tint = CharcoalGrey,
                        )
                        Text(
                            text = timestamFormatter(event.timestamp),
                            fontSize = 11.sp,
                            color = CharcoalGrey
                        )
                    }
                }
                item {
                    Text(
                        text = event.organization,
                        fontSize = 11.sp,
                        color = CharcoalGrey,
                    )
                }
                item {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = stringResource(R.string.profile_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
                    Text(
                        text = event.description,
                        fontSize = 15.sp,
                        color = CharcoalGrey
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.visit_organization),
                        fontSize = 15.sp,
                        color = Leaf,
                        textDecoration = TextDecoration.Underline
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    ) {
                        event.people.take(3).forEach { person ->
                            AsyncImage(
                                model = person.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                            )
                        }
                        Text(
                            text = if((event.people.size - 3) > 0) "+${event.people.size - 3}" else "",
                            color = CharcoalGrey,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(start = 8.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(White)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = Leaf,
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.download_report),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}