package com.kassaev.simbirsoft_1_git.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.util.ProfileScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val stateMutable = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Preview)
    private val state: StateFlow<ProfileScreenState> = stateMutable

    fun getStateFlow() = state

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
}