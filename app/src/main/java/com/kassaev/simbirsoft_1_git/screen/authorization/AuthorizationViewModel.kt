package com.kassaev.simbirsoft_1_git.screen.authorization

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
class AuthorizationViewModel: ViewModel() {

    private val credentialsSubject: BehaviorSubject<Credentials> = BehaviorSubject.createDefault(Credentials.default)
    private val isValidSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val disposables = CompositeDisposable()

    init {
        val disposable = credentialsSubject
            .distinctUntilChanged()
            .subscribe { currCredentials ->
                val isValid = currCredentials.email.length >= 6 && currCredentials.password.length >= 6
                isValidSubject.onNext(isValid)
            }
        disposables.add(disposable)
    }

    fun getCredentialsObservable(): Observable<Credentials> = credentialsSubject.hide()

    fun getIsValidObservable(): Observable<Boolean> = isValidSubject.hide()

    fun setEmail(email: String) {
        val current = credentialsSubject.value ?: Credentials.default
        credentialsSubject.onNext(current.copy(email = email))
    }

    fun setPassword(password: String) {
        val current = credentialsSubject.value ?: Credentials.default
        credentialsSubject.onNext(current.copy(password = password))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
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