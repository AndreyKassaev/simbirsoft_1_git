package com.kassaev.simbirsoft_1_git.core.repository.category

import com.kassaev.simbirsoft_1_git.core.util.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryMapFlow(): Flow<Map<String, Category>>
}