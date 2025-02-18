package com.kassaev.simbirsoft_1_git.screen.help

import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository

class HelpViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    fun getCategoryList() = categoryRepository.getCategoryList()
}