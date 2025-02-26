package com.kassaev.simbirsoft_1_git.repository.event

import android.content.res.AssetManager
import androidx.sqlite.db.SupportSQLiteQuery
import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.api.model.EventRequest
import com.kassaev.simbirsoft_1_git.database.dao.EventDao
import com.kassaev.simbirsoft_1_git.util.EventAsset
import com.kassaev.simbirsoft_1_git.util.EventMapper.apiListToDbList
import com.kassaev.simbirsoft_1_git.util.EventMapper.assetListToDbList
import com.kassaev.simbirsoft_1_git.util.EventMapper.dbListToUiList
import com.kassaev.simbirsoft_1_git.util.EventMapper.dbToUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.kassaev.simbirsoft_1_git.util.Event as UiEvent
import com.kassaev.simbirsoft_1_git.util.EventAsset as AssetEvent

class EventRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
    private val eventDao: EventDao,
) : EventRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        fetchEvents()
    }

    override fun getEventListFlow(): Flow<List<UiEvent>> =
        eventDao.getAll().map { dbListToUiList(it) }

    override suspend fun getEventById(id: String): UiEvent? =
        dbToUi(eventDao.findAndMarkAsWatched(id = id))

    override fun findByAnyWord(query: SupportSQLiteQuery): Flow<List<UiEvent>> =
        eventDao.findByAnyWord(query = query).map { dbListToUiList(it) }

    private fun fetchEvents(id: String = "") {
        scope.launch {
            runCatching {
                apiService.getEvents(request = EventRequest(id = id))
            }.onSuccess { eventList ->
                eventDao.insertAll(apiListToDbList(eventList))
            }.onFailure {
                eventDao.insertAll(assetListToDbList(loadEventListFromAssets()))
            }
        }
    }

    private fun loadEventListFromAssets(): List<AssetEvent> {
        return try {
            val inputStream = assetManager.open("events.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<List<EventAsset>>(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}