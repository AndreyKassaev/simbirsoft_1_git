package com.kassaev.simbirsoft_1_git.feature.help

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kassaev.simbirsoft_1_git.feature.help.screen.HelpScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun simpleUiTest() {
        composeRule.setContent {
            HelpScreen()
        }
        composeRule.onNodeWithTag("helpScreen").assertExists()
    }
}