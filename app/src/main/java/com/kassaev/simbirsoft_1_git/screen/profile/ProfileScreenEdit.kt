package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.model.Profile
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenEdit(
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
                }
            )
        }
    }
    Text("Editing...")
}
