package com.kassaev.simbirsoft_1_git.core.repository.category

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.core.api.ApiService
import com.kassaev.simbirsoft_1_git.core.database.dao.CategoryDao
import com.kassaev.simbirsoft_1_git.core.util.CategoryMapper.apiMapToDbList
import com.kassaev.simbirsoft_1_git.core.util.CategoryMapper.assetMapToDbList
import com.kassaev.simbirsoft_1_git.core.util.CategoryMapper.dbListToUiMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.kassaev.simbirsoft_1_git.core.util.CategoryAsset as AssetCategory

class CategoryRepositoryImpl(
    private val assetManager: AssetManager,
    private val apiService: ApiService,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        fetchCategories()
    }

    override fun getCategoryMapFlow() = categoryDao.getAll().map { dbListToUiMap(it) }

    private fun fetchCategories() {
        scope.launch {
            runCatching {
                apiService.getCategories()
            }.onSuccess{ categoryMap ->
                categoryDao.insertAll(apiMapToDbList(categoryMap))
            }.onFailure {
                categoryDao.insertAll(assetMapToDbList(loadCategoryMapFromAssets()))
            }
        }
    }

    private fun loadCategoryMapFromAssets(): Map<String, AssetCategory> {
        return try {
            val inputStream = assetManager.open("categories.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<Map<String,AssetCategory>>(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap()
        }
    }
}