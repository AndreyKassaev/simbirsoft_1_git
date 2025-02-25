package com.kassaev.simbirsoft_1_git.repository.category

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.api.ApiService
import com.kassaev.simbirsoft_1_git.util.CategoryMapper.mapApiToUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.kassaev.simbirsoft_1_git.util.Category as UiCategory

class CategoryRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
) : CategoryRepository {

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val categoryMapMutableFlow = MutableStateFlow<Map<String, UiCategory>>(emptyMap())
    private val categoryMapFlow: StateFlow<Map<String, UiCategory>> = categoryMapMutableFlow

    init {
        fetchCategories()
    }

    override fun getCategoryMapFlow() = categoryMapFlow

    private fun fetchCategories() {
        scope.launch {
            runCatching {
                apiService.getCategories()
            }.onSuccess{ categoryMap ->
                categoryMapMutableFlow.update {
                    mapApiToUi(categoryMap)
                }
            }.onFailure {
                categoryMapMutableFlow.update {
                    loadCategoryMapFromAssets()
                }
            }
        }
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