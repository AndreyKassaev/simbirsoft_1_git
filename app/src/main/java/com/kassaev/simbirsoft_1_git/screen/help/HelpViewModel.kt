package com.kassaev.simbirsoft_1_git.screen.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.util.HelpCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class HelpViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val stateMutable = MutableStateFlow<HelpScreenState>(HelpScreenState.Loading())
    private val state: StateFlow<HelpScreenState> = stateMutable

    private val executor = Executors.newSingleThreadExecutor()

    init {
        setCategoryList()
    }

    fun getStateFlow() = state

    private fun setCategoryList() {
        executor.execute {
            Thread.sleep(5000L)
            stateMutable.update {
                HelpScreenState.Success(
                    data = categoryRepository.getCategoryList()
                )
            }
        }
    }
}