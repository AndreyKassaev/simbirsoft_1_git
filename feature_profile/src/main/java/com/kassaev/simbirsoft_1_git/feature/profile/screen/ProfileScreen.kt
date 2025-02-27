package com.kassaev.simbirsoft_1_git.feature.profile.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    setTopAppBar: (topAppBar: @Composable () -> Unit) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    scrollBehavior: TopAppBarScrollBehavior
) {

    val state by viewModel.getStateFlow().collectAsStateWithLifecycle()
    val profile by viewModel.getProfileFlow().collectAsStateWithLifecycle()

    when (state) {
        ProfileScreenState.Edit -> {
            ProfileScreenEdit(
                setTopAppBar = setTopAppBar,
                switchState = viewModel::switchState,
                profile = profile,
                setFirstName = viewModel::setFirstName,
                setLastName = viewModel::setLastName,
                setBirthDate = viewModel::setBirthDate,
                setOccupation = viewModel::setOccupation,
                setPassword = viewModel::setPassword,
                setPhoto = viewModel::setPhoto,
                scrollBehavior = scrollBehavior
            )
        }

        ProfileScreenState.Preview -> {
            ProfileScreenPreview(
                setTopAppBar = setTopAppBar,
                switchState = viewModel::switchState,
                profile = profile,
                switchPush = viewModel::switchPush,
                scrollBehavior = scrollBehavior
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPrev() {

}