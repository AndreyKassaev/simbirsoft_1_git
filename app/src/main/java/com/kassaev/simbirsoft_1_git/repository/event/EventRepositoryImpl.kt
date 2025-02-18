package com.kassaev.simbirsoft_1_git.repository.event

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.util.Event
import kotlinx.serialization.json.Json

class EventRepositoryImpl(
    private val assetManager: AssetManager
): EventRepository {

    private var eventList = emptyList<Event>()

    override fun getEventList(): List<Event> {
        if (eventList.isEmpty()) {
            eventList = loadEventListFromAssets()
        }
        return eventList
    }

    override fun getEventById(id: String): Event? =
        eventList.find { event -> event.id == id }


    private fun loadEventListFromAssets(): List<Event> {
        return try {
            val inputStream = assetManager.open("events.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}