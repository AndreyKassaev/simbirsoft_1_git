package com.kassaev.simbirsoft_1_git.repository.category

import com.kassaev.simbirsoft_1_git.util.HelpCategory

interface CategoryRepository {
    fun getCategoryList(): List<HelpCategory>
}