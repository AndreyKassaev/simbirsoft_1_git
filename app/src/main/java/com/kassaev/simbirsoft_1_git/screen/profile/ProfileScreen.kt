package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kassaev.simbirsoft_1_git.util.ProfileScreenState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val state by viewModel.getStateFlow().collectAsStateWithLifecycle()
    val profile by viewModel.getProfileFlow().collectAsStateWithLifecycle()
    val switchState = viewModel::switchState
    val switchPush = viewModel::switchPush

    when (state) {
        ProfileScreenState.Edit -> {
            ProfileScreenEdit(
                setTopAppBar = setTopAppBar,
                switchState = switchState,
                profile = profile,
                switchPush = switchPush
            )
        }

        ProfileScreenState.Preview -> {
            ProfileScreenPreview(
                setTopAppBar = setTopAppBar,
                switchState = switchState,
                profile = profile,
                switchPush = switchPush
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPrev() {

}