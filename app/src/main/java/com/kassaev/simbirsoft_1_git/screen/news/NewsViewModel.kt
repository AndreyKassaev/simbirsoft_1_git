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
import com.kassaev.simbirsoft_1_git.repository.event.EventRepository
import com.kassaev.simbirsoft_1_git.service.EventAssetReaderService
import com.kassaev.simbirsoft_1_git.util.Event
import com.kassaev.simbirsoft_1_git.util.FilterSwitchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val application: Application,
    private val context: Context
) : AndroidViewModel(application) {

    private val newsListMutable = MutableStateFlow<List<Event>>(emptyList())
    private val newsList: StateFlow<List<Event>> = newsListMutable

    private val stateMutable = MutableStateFlow<NewsScreenState>(NewsScreenState.Loading())
    private val state: StateFlow<NewsScreenState> = stateMutable

    private val filterSwitchStateMutable = MutableStateFlow<FilterSwitchState>(FilterSwitchState.default)
    private val filterSwitchState: StateFlow<FilterSwitchState> = filterSwitchStateMutable

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

    private fun unBindService() {
        context.unbindService(connection)
    }

    init {
        getNewsList()
    }

    fun getStateFlow() = state

    fun getFilterSwitchStateFlow() = filterSwitchState

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

    private fun getNewsList() {
        val intent = Intent(context, EventAssetReaderService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.startService(intent)
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

}