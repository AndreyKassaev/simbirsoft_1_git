package com.kassaev.simbirsoft_1_git.screen.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
): ViewModel() {

    private val stateMutableFlow = MutableStateFlow<HelpScreenState>(HelpScreenState.Loading())
    private val stateFlow: StateFlow<HelpScreenState> = stateMutableFlow

    init {
        loadCategories()
    }

    fun getStateFlow() = stateFlow

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategoryMapFlow().collectLatest { categoryMap ->
                if (categoryMap.isNotEmpty()) {
                    stateMutableFlow.update {
                        HelpScreenState.Success(
                            data = categoryMap
                        )
                    }
                }
            }
        }
    }
}