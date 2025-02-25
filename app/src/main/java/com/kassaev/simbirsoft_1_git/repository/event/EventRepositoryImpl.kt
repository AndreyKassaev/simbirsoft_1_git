package com.kassaev.simbirsoft_1_git.repository.event

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.api.model.EventRequest
import com.kassaev.simbirsoft_1_git.util.EventAsset
import com.kassaev.simbirsoft_1_git.util.EventMapper.apiListToUiList
import com.kassaev.simbirsoft_1_git.util.EventMapper.assetListToUiList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import com.kassaev.simbirsoft_1_git.util.Event as UiEvent
import io.reactivex.rxjava3.core.Observable
import com.kassaev.simbirsoft_1_git.api.model.Event as ApiEvent
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.serialization.json.Json

class EventRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
) : EventRepository {

    private var eventList = emptyList<UiEvent>()

    private val compositeDisposable = CompositeDisposable()
    private val eventListSubject: BehaviorSubject<List<UiEvent>> =
        BehaviorSubject.create()
    private val eventListObservable: Observable<List<UiEvent>> = eventListSubject.hide()

    init {
        fetchEvents()
    }

    override fun getEvents(categoryId: String): Single<List<ApiEvent>> {
        return apiService.getEvents(EventRequest(categoryId))
    }

    override fun getEventListObservable(): Observable<List<UiEvent>> = eventListObservable

    override fun getEventList(): List<UiEvent> {
        if (eventList.isEmpty()) {
            eventList = loadEventListFromAssets()
        }
        return eventList
    }

    override fun getEventById(id: String): UiEvent?{
        return try {
            val eventList = eventListObservable.blockingFirst()
            eventList.find { it.id == id }
        } catch (e: Exception) {
            loadEventListFromAssets().find { it.id == id }
        }
    }

    private fun fetchEvents(id: String = "") {
        val disposable = apiService.getEvents(EventRequest(id = id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { eventList ->
                    eventListSubject.onNext(
                        apiListToUiList(eventList)
                    )
                }, { error ->
                    eventListSubject.onNext(loadEventListFromAssets())
                }
            )
        compositeDisposable.add(disposable)
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