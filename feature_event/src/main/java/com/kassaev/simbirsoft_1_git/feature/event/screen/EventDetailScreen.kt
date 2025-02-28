package com.kassaev.simbirsoft_1_git.feature.event.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.ui.theme.BlueGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.White
import com.kassaev.simbirsoft_1_git.core.util.Event
import com.kassaev.simbirsoft_1_git.core.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.core.util.timestamFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: EventDetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.getStateFlow().collectAsStateWithLifecycle()
    val isDialogOpen by viewModel.getIsDialogOpenFlow().collectAsStateWithLifecycle()
    val donationAmount by viewModel.getDonationAmount().collectAsStateWithLifecycle()

    when(state) {
        is EventDetailScreenState.Success -> {
            EventDetailScreenSuccess(
                setTopAppBar = setTopAppBar,
                scrollBehavior = scrollBehavior,
                event = (state as EventDetailScreenState.Success).data,
                navController = navController,
                setIsDialogOpen = viewModel::setIsDialogOpen,
                isDialogOpen = isDialogOpen,
                donationAmount = donationAmount,
                setDonationAmount = viewModel::setDonationAmount
            )
        }
        is EventDetailScreenState.Loading -> {
            EventDetailScreenLoading()
        }
        is EventDetailScreenState.Error -> {
            EventDetailScreenError()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreenSuccess(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    event: Event,
    navController: NavController,
    setIsDialogOpen: (Boolean) -> Unit,
    isDialogOpen: Boolean,
    donationAmount: String,
    setDonationAmount: (String) -> Unit,
) {
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
    if (isDialogOpen) {
        Dialog(
            onDismissRequest = {
                setDonationAmount("")
                setIsDialogOpen(false)
            }
        ) {
            var isValidInput by remember {
                mutableStateOf(false)
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(White)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = donationAmount,
                    onValueChange = { input ->
                        setDonationAmount(input)
                        val number = input.toIntOrNull()
                        isValidInput = (number != null && number in 1..9_999_999)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ruble),
                            contentDescription = stringResource(R.string.ruble),
                            tint = CharcoalGrey
                        )
                    },
                    label = {
                        Text(
                            modifier = Modifier
                                .background(Color.Unspecified),
                            text = stringResource(R.string.amount),
                            color = Leaf
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Send
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = CharcoalGrey,
                        focusedIndicatorColor = Leaf,
                        unfocusedIndicatorColor = Leaf,
                        disabledContainerColor = White,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        cursorColor = Leaf,

                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        //TODO workManager
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Leaf,
                        contentColor = White
                    ),
                    enabled = isValidInput
                ) {
                    Text(
                        text = stringResource(R.string.help)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.9F),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { }
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
                    model = event.imageUrlList.firstOrNull(),
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
                        text = if ((event.people.size - 3) > 0) "+${event.people.size - 3}" else "",
                        color = CharcoalGrey,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(White)
                .fillMaxHeight(0.1F)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    setIsDialogOpen(true)
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
                    text = stringResource(R.string.help),
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun EventDetailScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Leaf)
    }
}

@Composable
fun EventDetailScreenError() {
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