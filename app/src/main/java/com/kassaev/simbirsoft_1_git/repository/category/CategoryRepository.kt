package com.kassaev.simbirsoft_1_git.repository.category

import com.kassaev.simbirsoft_1_git.util.Category
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepository {
    fun getCategoryMapFlow(): StateFlow<Map<String, Category>>
}