package com.kassaev.simbirsoft_1_git.repository.event

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.api.model.EventRequest
import com.kassaev.simbirsoft_1_git.util.EventAsset
import com.kassaev.simbirsoft_1_git.util.EventMapper.apiListToUiList
import com.kassaev.simbirsoft_1_git.util.EventMapper.assetListToUiList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.kassaev.simbirsoft_1_git.util.Event as UiEvent

class EventRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
) : EventRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val eventListMutableFlow = MutableStateFlow<List<UiEvent>>(emptyList())
    private val eventListFlow: StateFlow<List<UiEvent>> = eventListMutableFlow

    init {
        fetchEvents()
    }

    override fun getEventListFlow() = eventListFlow

    override fun getEventById(id: String): UiEvent?{
        return eventListFlow.value.find { it.id == id }
    }

    private fun fetchEvents(id: String = "") {
        scope.launch {
            runCatching {
                apiService.getEvents(request = EventRequest(id = id))
            }.onSuccess { eventList ->
                eventListMutableFlow.update {
                    apiListToUiList(eventList)
                }
            }.onFailure {
                eventListMutableFlow.update {
                    loadEventListFromAssets()
                }
            }
        }
    }

    private fun loadEventListFromAssets(): List<UiEvent> {
        return try {
            val inputStream = assetManager.open("events.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            assetListToUiList(Json.decodeFromString<List<EventAsset>>(json))
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}