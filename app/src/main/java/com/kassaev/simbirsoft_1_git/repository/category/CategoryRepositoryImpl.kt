package com.kassaev.simbirsoft_1_git.repository.category

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.util.CategoryMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.serialization.json.Json
import com.kassaev.simbirsoft_1_git.util.Category as UiCategory

class CategoryRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
) : CategoryRepository {

    private val compositeDisposable = CompositeDisposable()
    private val categoryMapSubject: BehaviorSubject<Map<String, UiCategory>> =
        BehaviorSubject.create()
    private val categoryMapObservable: Observable<Map<String, UiCategory>> =
        categoryMapSubject.hide()

    init {
        fetchCategories()
    }

    override fun getCategoryMapObservable(): Observable<Map<String, UiCategory>> =
        categoryMapObservable

    private fun fetchCategories() {
        val disposable = apiService.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { categoryMap ->
                    categoryMapSubject.onNext(CategoryMapper.mapApiToUi(categoryMap))
                }, { error ->
                    categoryMapSubject.onNext(loadCategoryMapFromAssets())
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun loadCategoryMapFromAssets(): Map<String, UiCategory> {
        return try {
            val inputStream = assetManager.open("categories.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap()
        }
    }
}