package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar
import com.kassaev.simbirsoft_1_git.util.ProfileScreenState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val state by viewModel.getStateFlow().collectAsStateWithLifecycle()
    val switchState = viewModel::switchState

    when (state) {
        ProfileScreenState.Edit -> {
            ProfileScreenEdit(
                setTopAppBar = setTopAppBar,
                switchState = switchState
            )
        }

        ProfileScreenState.Preview -> {
            ProfileScreenPreview(
                setTopAppBar = setTopAppBar,
                switchState = switchState
            )
        }
    }
}

@Composable
fun ProfileScreenEdit(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    switchState: () -> Unit
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

@Composable
fun ProfileScreenPreview(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    switchState: () -> Unit
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
    Text("Previewing...")
}

@Preview
@Composable
private fun ProfileScreenPrev() {

}