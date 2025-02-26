package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.api.model.Category as ApiCategory
import com.kassaev.simbirsoft_1_git.util.Category as UiCategory
import com.kassaev.simbirsoft_1_git.database.entity.Category as DbCategory
import com.kassaev.simbirsoft_1_git.util.CategoryAsset as AssetCategory

object CategoryMapper {

    fun mapApiToUi(apiCategories: Map<String, ApiCategory>): Map<String, UiCategory> {
        return apiCategories.mapValues { entry ->
            with(entry.value){
                UiCategory(
                    id = id,
                    nameEn = nameEn,
                    name = name,
                    image = image
                )
            }
        }
    }

    fun dbToUi(dbCategory: DbCategory): UiCategory =
        with(dbCategory) {
            UiCategory(
                id = id,
                nameEn = nameEn,
                name = name,
                image = image
            )
        }

    fun apiToDb(apiCategory: ApiCategory): DbCategory =
        with(apiCategory) {
            DbCategory(
                id = id,
                nameEn = nameEn,
                name = name,
                image = image
            )
        }

    fun assetToDb(assetCategory: AssetCategory): DbCategory =
        with(assetCategory) {
            DbCategory(
                id = id,
                nameEn = nameEn,
                name = name,
                image = image
            )
        }

    fun dbListToUiMap(dbCategoryList: List<DbCategory>): Map<String, UiCategory> =
        dbCategoryList.map { dbToUi(it) }.associateBy { it.nameEn }

    fun apiMapToDbList(apiCategoryMap: Map<String, ApiCategory>): List<DbCategory> =
        apiCategoryMap.values.toList().map { apiToDb(it) }

    fun assetMapToDbList(assetCategoryMap: Map<String, AssetCategory>): List<DbCategory> =
        assetCategoryMap.values.toList().map { assetToDb(it) }
}