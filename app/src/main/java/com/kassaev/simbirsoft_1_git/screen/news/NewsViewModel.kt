package com.kassaev.simbirsoft_1_git.screen.news

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.service.EventAssetReaderService
import com.kassaev.simbirsoft_1_git.util.FilterSwitchState
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val application: Application,
    private val context: Context
) : AndroidViewModel(application) {

    private val stateMutable = MutableStateFlow<NewsScreenState>(NewsScreenState.Loading())
    private val state: StateFlow<NewsScreenState> = stateMutable

    private val filterSwitchStateMutable = MutableStateFlow<FilterSwitchState>(FilterSwitchState.default)
    private val filterSwitchState: StateFlow<FilterSwitchState> = filterSwitchStateMutable

    private val unWatchedNewsCountMutable = MutableStateFlow<Int>(0)
    private val unWatchedNewsCount: StateFlow<Int> = unWatchedNewsCountMutable

    private val isServiceStartedMutable = MutableStateFlow(false)
    private val isServiceStarted: StateFlow<Boolean> = isServiceStartedMutable

    private var eventAssetReaderService: EventAssetReaderService? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val binder = service as EventAssetReaderService.LocalBinder
            eventAssetReaderService = binder.getService()

            viewModelScope.launch {
                eventAssetReaderService?.readFile()?.await()?.let { assetList ->
                    stateMutable.update {
                        NewsScreenState.Success(data = assetList)
                    }
                    unBindService()
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }

    init {
        updateUnWatchedNewsCount()
    }

    fun getUnWatchedNewsCountFlow() = unWatchedNewsCount

    fun getStateFlow() = state

    fun getFilterSwitchStateFlow() = filterSwitchState

    fun getIsServiceStarted() = isServiceStarted

    fun setFilterSwitchMoneyState(state: Boolean) {
        viewModelScope.launch {
            filterSwitchStateMutable.update { currentState ->
                currentState.copy(
                    money = state
                )
            }
        }
    }

    fun setFilterSwitchStuffState(state: Boolean) {
        viewModelScope.launch {
            filterSwitchStateMutable.update { currentState ->
                currentState.copy(
                    stuff = state
                )
            }
        }
    }

    fun setIsWatched(eventId: String) {
        viewModelScope.launch {
            stateMutable.update { currState ->
                if (currState is NewsScreenState.Success) {
                    val updatedEventList = currState.data.map { event ->
                        if (event.id == eventId) {
                            event.copy(
                                isWatched = true
                            )
                        } else {
                            event
                        }
                    }
                    NewsScreenState.Success(data = updatedEventList)
                } else {
                    currState
                }
            }
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            isServiceStartedMutable.update {
                true
            }
        }
        val intent = Intent(context, EventAssetReaderService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        context.unbindService(connection)
    }

    private fun updateUnWatchedNewsCount() {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        scope.launch {
            state.collectLatest { currState ->
                if (currState is NewsScreenState.Success) {
                    unWatchedNewsCountMutable.update {
                        currState.data.count { event -> !event.isWatched }
                    }
                }
            }
        }
    }
}