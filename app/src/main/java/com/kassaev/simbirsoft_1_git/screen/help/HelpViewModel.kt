package com.kassaev.simbirsoft_1_git.screen.help

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.util.RxJavaTask
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class HelpViewModel(
    private val categoryRepository: CategoryRepository,
    private val rxJavaTask: RxJavaTask
): ViewModel() {

    private val stateSubject = BehaviorSubject.createDefault<HelpScreenState>(HelpScreenState.Loading())
    private val disposables = CompositeDisposable()
    init {
        loadCategories()
        triggerRxJavaTask()
    }

    private fun triggerRxJavaTask() {
        val randomDataObservable = rxJavaTask.getRandomDataObservable()
        val disposables = CompositeDisposable()
        val randomDataObservableDisposable = randomDataObservable.subscribe(
            { data -> Log.d("RX_THREAD_task", "Random data: $data") },
            { error -> Log.e("RX_THREAD_task", "Error: ${error.localizedMessage}") }
        )

        val combinedObservable = rxJavaTask.combineObservables(
            categoryRepository.getCategoryListObservable(),
            randomDataObservable
        )

        val combinedObservableDisposable = combinedObservable.subscribe(
            { combinedData -> Log.d("RX_THREAD_task", "Combined Data: $combinedData") },
            { error -> Log.e("RX_THREAD_task", "Error in combine: ${error.localizedMessage}") }
        )
        disposables.add(randomDataObservableDisposable)
        disposables.add(combinedObservableDisposable)
    }

    fun getStateObservable(): Observable<HelpScreenState> = stateSubject.hide()

    private fun loadCategories() {
        val disposable = categoryRepository.getCategoryListObservable()
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnNext { Log.d("RX_THREAD", "Processing data on: ${Thread.currentThread().name}") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    stateSubject.onNext(HelpScreenState.Success(data))
                    Log.d("RX_THREAD", "Emitting result on: ${Thread.currentThread().name}")
                },
                { error ->
                    stateSubject.onNext(HelpScreenState.Failure(error.message.toString()))
                    Log.e("RX_THREAD", "Error on: ${Thread.currentThread().name}", error)
                }
            )
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        rxJavaTask.clearDisposables()
    }
}