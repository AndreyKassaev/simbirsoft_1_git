package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.model.Profile
import com.kassaev.simbirsoft_1_git.util.ProfileScreenState
import com.kassaev.simbirsoft_1_git.util.getMockProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val stateMutable = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Preview)
    private val state: StateFlow<ProfileScreenState> = stateMutable

    private val profileMutable = MutableStateFlow<Profile>(getMockProfile())
    private val profile: StateFlow<Profile> = profileMutable

    fun getStateFlow() = state

    fun getProfileFlow() = profile

    fun switchState(){
        viewModelScope.launch {
            stateMutable.update { state ->
                if (state is ProfileScreenState.Preview){
                    ProfileScreenState.Edit
                } else {
                    ProfileScreenState.Preview
                }
            }
        }
    }

    fun switchPush(){
        viewModelScope.launch {
            profileMutable.update { profile ->
                val currIsPushEnabled = profile.isPushEnabled
                profile.copy(
                    isPushEnabled = !currIsPushEnabled
                )
            }
        }
    }
}