package com.kassaev.simbirsoft_1_git.feature.help

import android.app.Application
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.kassaev.simbirsoft_1_git.core.api.ApiService
import com.kassaev.simbirsoft_1_git.core.repository.category.CategoryRepository
import com.kassaev.simbirsoft_1_git.core.util.Category
import com.kassaev.simbirsoft_1_git.feature.help.screen.HelpScreen
import com.kassaev.simbirsoft_1_git.feature.help.screen.HelpViewModel
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun simpleUiTest() {
        composeRule.setContent {
            HelpScreen()
        }
        composeRule.onNodeWithTag("helpScreen").assertExists()
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun foo() {
        composeRule.setContent {
            HelpScreen(
                setTopAppBar = {},
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
                navController = rememberNavController(),
                viewModel = HelpViewModel(categoryRepository = categoryRepository)
            )
        }
        runBlocking {
            delay(3000L)
            composeRule.onNode(hasScrollToIndexAction()).assertExists()
        }
    }
}

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}