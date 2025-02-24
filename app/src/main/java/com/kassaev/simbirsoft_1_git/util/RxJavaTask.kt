package com.kassaev.simbirsoft_1_git.util

import android.util.Log
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RxJavaTask(
    private val categoryRepository: CategoryRepository
) {

    private val disposables = CompositeDisposable()

    init {
        startTask()
    }

    private fun startTask() {
        val disposable = categoryRepository.getCategoryMapObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnNext {
                Log.d(
                    "RX_THREAD_TASK",
                    "After first observeOn: ${Thread.currentThread().name}"
                )
            }
            .observeOn(Schedulers.newThread())
            .doOnNext {
                Log.d(
                    "RX_THREAD_TASK",
                    "After second observeOn: ${Thread.currentThread().name}"
                )
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe({ data ->
                Log.d("RX_THREAD_TASK", "Final subscriber thread: ${Thread.currentThread().name}")
            }, { error ->
                Log.e("RX_THREAD_TASK", "Error: ${error.localizedMessage}")
            })

        disposables.add(disposable)
    }

    fun getRandomDataObservable(): Observable<List<String>> {
        return Observable.fromCallable {
            Log.d("RX_THREAD", "Generating random data on: ${Thread.currentThread().name}")
            Thread.sleep(2000)
            listOf("Random1", "Random2", "Random3")
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.newThread())
    }

    fun combineObservables(
        repoObservable: Observable<Map<String, Category>>,
        randomObservable: Observable<List<String>>
    ): Observable<Pair<Map<String, Category>, List<String>>> {
        return Observable.zip(repoObservable, randomObservable) { categories, randomData ->
            Log.d("RX_THREAD", "Combining results on: ${Thread.currentThread().name}")
            categories to randomData
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun clearDisposables() {
        disposables.clear()
    }
}
