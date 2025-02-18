package com.kassaev.simbirsoft_1_git.repository.category

import android.content.res.AssetManager
import com.kassaev.simbirsoft_1_git.util.HelpCategory
import kotlinx.serialization.json.Json

class CategoryRepositoryImpl(
    private val assetManager: AssetManager
): CategoryRepository {
    private var categoryList = emptyList<HelpCategory>()

    override fun getCategoryList(): List<HelpCategory> {
        if (categoryList.isEmpty()) {
            categoryList = loadCategoryListFromAssets()
        }
        return categoryList
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