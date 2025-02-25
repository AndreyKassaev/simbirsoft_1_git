package com.kassaev.simbirsoft_1_git.screen.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class AuthorizationViewModel: ViewModel() {

    private val credentialsFlowMutable = MutableStateFlow<Credentials>(Credentials.default)
    private val credentialsFlow: StateFlow<Credentials> = credentialsFlowMutable

    private val isValidFlowMutable = MutableStateFlow<Boolean>(false)
    private val isValidFlow: StateFlow<Boolean> = isValidFlowMutable

    init {
        viewModelScope.launch {
            credentialsFlow.collectLatest { currentCredentials ->
                isValidFlowMutable.update {
                    currentCredentials.password.length >= 6 && currentCredentials.email.length >= 6
                }
            }
        }
    }

    fun getCredentialsFlow() = credentialsFlow

    fun getIsValidFlow() = isValidFlow

    fun setEmail(email: String) {
        viewModelScope.launch {
            credentialsFlowMutable.update { currentCredentials ->
                currentCredentials.copy(
                    email = email
                )
            }
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            credentialsFlowMutable.update { currentCredentials ->
                currentCredentials.copy(
                    password = password
                )
            }
        }
    }
}

data class Credentials(
    val email: String,
    val password: String,
) {
    companion object {
        val default = Credentials(
            email = "",
            password = ""
        )
    }
}