package com.kassaev.simbirsoft_1_git.feature.profile.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.uikit.TextField
import com.kassaev.simbirsoft_1_git.core.uikit.TextFieldPassword
import com.kassaev.simbirsoft_1_git.feature.profile.model.Profile
import com.kassaev.simbirsoft_1_git.core.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.core.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.feature.profile.util.DatePicker
import com.kassaev.simbirsoft_1_git.feature.profile.util.ProfileDialog

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenEdit(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    switchState: () -> Unit,
    profile: Profile,
    setFirstName: (String) -> Unit,
    setLastName: (String) -> Unit,
    setBirthDate: (String) -> Unit,
    setOccupation: (String) -> Unit,
    setPassword: (String) -> Unit,
    setPhoto: (String?) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    var (isDialogOpen, setIsDialogOpen) = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = stringResource(R.string.edit),
                actions = {
                    IconButton(
                        onClick = {
                            switchState()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check_circle),
                            contentDescription = stringResource(R.string.save_edit_profile)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            switchState()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.cancel_edit_profile)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    }
    var rowHeight by remember {
        mutableStateOf(0.dp)
    }
    Column {
        if (isDialogOpen) {
            ProfileDialog(
                setIsDialogOpen = setIsDialogOpen,
                setPhoto = setPhoto
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(4f)
                    .clip(CircleShape)
                    .clickable {
                        setIsDialogOpen(true)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                val textHorizontalPadding = minWidth / 3
                rowHeight = minWidth
                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(1F),
                    model = profile.imageUrl,
                    contentDescription = stringResource(R.string.profile_image),
                    placeholder = painterResource(R.drawable.profile),
                    error = painterResource(R.drawable.profile),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .background(
                            Color.Gray.copy(alpha = 0.6f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.change_photo),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 11.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = textHorizontalPadding),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(6f)
                    .height(rowHeight),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TextField(
                    label = stringResource(R.string.firstName),
                    value = profile.firstName,
                    onValueChange = setFirstName,
                )
                TextField(
                    label = stringResource(R.string.lastName),
                    value = profile.lastName,
                    onValueChange = setLastName,
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(
            modifier = Modifier
                .height(2.dp),
            color = DividerGrey,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            DatePicker(
                onBirthDateSelected = setBirthDate,
                birthDate = profile.birthDate
            )
            TextField(
                label = stringResource(R.string.occupation_ext),
                value = profile.occupation,
                onValueChange = setOccupation,
            )
            TextFieldPassword(
                label = stringResource(R.string.password),
                value = profile.password,
                onValueChange = setPassword,
            )
        }
    }
}
