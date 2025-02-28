package com.kassaev.simbirsoft_1_git.feature.profile.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.feature.profile.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val stateMutable = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Preview)
    private val state: StateFlow<ProfileScreenState> = stateMutable

    private val profileMutable = MutableStateFlow<Profile>(Profile.default)
    private val profile: StateFlow<Profile> = profileMutable

    fun getStateFlow() = state

    fun getProfileFlow() = profile

    fun setFirstName(firstName: String) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    firstName = firstName
                )
            }
        }
    }

    fun setLastName(lastName: String) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    lastName = lastName
                )
            }
        }
    }

    fun setBirthDate(birthDate: String) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    birthDate = birthDate
                )
            }
        }
    }

    fun setOccupation(occupation: String) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    occupation = occupation
                )
            }
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    password = password
                )
            }
        }
    }

    fun setPhoto(imageUrl: String?) {
        viewModelScope.launch {
            profileMutable.update { profile ->
                profile.copy(
                    imageUrl = imageUrl
                )
            }
        }
    }

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