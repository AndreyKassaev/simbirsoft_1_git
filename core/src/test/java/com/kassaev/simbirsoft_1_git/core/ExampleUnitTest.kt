package com.kassaev.simbirsoft_1_git.core

import com.kassaev.simbirsoft_1_git.core.util.EventMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import com.kassaev.simbirsoft_1_git.core.api.model.Event as ApiEvent

class ExampleUnitTest {

    @Test
    fun `ids are equals`() {
        val apiEvent = ApiEvent.default
        val uiEvent = EventMapper.apiToUi(apiEvent)
        assertEquals(apiEvent.id, uiEvent.id)
    }
}