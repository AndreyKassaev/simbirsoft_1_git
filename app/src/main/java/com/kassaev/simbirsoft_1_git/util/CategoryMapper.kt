package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.api.model.Category as ApiCategory
import com.kassaev.simbirsoft_1_git.util.Category as UiCategory

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
}