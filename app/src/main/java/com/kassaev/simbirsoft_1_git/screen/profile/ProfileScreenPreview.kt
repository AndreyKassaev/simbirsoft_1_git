package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.model.Friend
import com.kassaev.simbirsoft_1_git.model.Profile
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGreyLight
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Melon
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@Composable
fun ProfileScreenPreview(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    switchState: () -> Unit,
    profile: Profile,
    switchPush: () -> Unit
) {
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = R.string.profile,
                actions = {
                    IconButton(
                        onClick = {
                            switchState()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = stringResource(R.string.edit_profile)
                        )
                    }
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        LazyColumn {
            item {
                ProfileImage(imageUrl = profile.imageUrl)
            }
            item {
                ProfileData(
                    firstName = profile.firstName,
                    lastName = profile.lastName,
                    birthDate = profile.birthDate,
                    occupation = profile.occupation,
                    isPushEnabled = profile.isPushEnabled,
                    switchPush = switchPush
                )
            }
            item {
                ProfileFriendList(friendList = profile.friendList)
            }
        }
        LogoutButton()
    }
}

@Composable
private fun BoxScope.LogoutButton() {
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .background(White)
    ) {
        HorizontalDivider(modifier = Modifier.height(1.dp), color = LightGrey)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            onClick = {

            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = Leaf
            )
        ) {
            Text(
                text = stringResource(R.string.logout),
            )
        }
    }
}

@Composable
private fun ProfileFriendList(friendList: List<Friend>) {
    HorizontalDivider(modifier = Modifier.height(1.dp), color = LightGrey)
    Text(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        text = stringResource(R.string.your_friends),
        fontSize = 13.sp,
        color = CharcoalGreyLight
    )
    friendList.forEach { friend ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.profile),
                contentDescription = stringResource(R.string.friend)
            )
            Text(
                text = "${friend.firstName} ${friend.lastName}",
                fontSize = 17.sp,
                color = CharcoalGrey
            )
        }
    }
}

@Composable
private fun ProfileImage(imageUrl: String) {
    Image(
        modifier = Modifier
            .fillMaxWidth(),
        painter = painterResource(R.drawable.profile),
        contentDescription = stringResource(R.string.profile_image),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ProfileData(
    firstName: String,
    lastName: String,
    birthDate: String,
    occupation: String,
    isPushEnabled: Boolean,
    switchPush: () -> Unit
) {
    Text(
        modifier = Modifier
            .padding(20.dp),
        text = "$lastName $firstName",
        fontSize = 21.sp,
        color = CharcoalGrey
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp, bottom = 6.dp),
        text = stringResource(R.string.birth_date),
        fontSize = 13.sp,
        color = CharcoalGreyLight
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp, bottom = 20.dp),
        text = birthDate,
        fontSize = 17.sp,
        color = CharcoalGrey
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp, bottom = 6.dp),
        text = stringResource(R.string.occupation),
        fontSize = 13.sp,
        color = CharcoalGreyLight
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp, bottom = 30.dp),
        text = occupation,
        fontSize = 17.sp,
        color = CharcoalGrey
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.enable_push),
            fontSize = 17.sp,
            color = CharcoalGrey
        )
        Switch(
            checked = isPushEnabled,
            onCheckedChange = {
                switchPush()
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Leaf,
                uncheckedTrackColor = Melon,
                uncheckedThumbColor = White,
            )
        )
    }
}
