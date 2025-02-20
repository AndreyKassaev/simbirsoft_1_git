package com.kassaev.simbirsoft_1_git.screen.help

import androidx.lifecycle.ViewModel
import com.kassaev.simbirsoft_1_git.repository.category.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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