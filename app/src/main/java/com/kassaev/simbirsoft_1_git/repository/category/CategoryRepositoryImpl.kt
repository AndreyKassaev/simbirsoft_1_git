package com.kassaev.simbirsoft_1_git.repository.category

import android.content.res.AssetManager
import android.util.Log
import com.kassaev.simbirsoft_1_git.util.HelpCategory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.json.Json

class CategoryRepositoryImpl(
    private val assetManager: AssetManager
): CategoryRepository {
private var categoryList = emptyList<HelpCategory>()

    override fun getCategoryListObservable(): Observable<List<HelpCategory>> {
        return Observable.create<List<HelpCategory>> { emitter ->
            Log.d("RX_THREAD", "Creating Observable on: ${Thread.currentThread().name}")

            if (categoryList.isEmpty()) {
                categoryList = loadCategoryListFromAssets()
            }
            emitter.onNext(categoryList)
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.d("RX_THREAD", "Loaded categories on: ${Thread.currentThread().name}") }
    }

    private fun loadCategoryListFromAssets(): List<HelpCategory> {
        return try {
            val inputStream = assetManager.open("help_categories.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}