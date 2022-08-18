package com.murilobonetti.events.eventslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.murilobonetti.events.MainCoroutineRule
import com.murilobonetti.events.getOrAwaitValue
import com.murilobonetti.events.network.ApiStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventsViewModelTest {

    private lateinit var eventsViewModel: FakeEventsViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        eventsViewModel = FakeEventsViewModel()
    }

    @Test
    fun `Get all events should return five events`() {
        eventsViewModel.getAllEvents()
        val value = eventsViewModel.events.getOrAwaitValue()
        assertEquals(value.size, 5)
    }

    @Test
    fun `Get all events connection error should return empty list`() {
        eventsViewModel.setReturnError(true)

        eventsViewModel.getAllEvents()
        val value = eventsViewModel.events.getOrAwaitValue()
        assertEquals(value.size, 0)

        eventsViewModel.setReturnError(false)
    }

    @Test
    fun `Get all events status should end DONE`() {
        eventsViewModel.getAllEvents()

        val value = eventsViewModel.status.value

        assertEquals(value, ApiStatus.DONE)
    }

    @Test
    fun `Get all events status should end ERROR`() {
        eventsViewModel.setReturnError(true)
        eventsViewModel.getAllEvents()

        val value = eventsViewModel.status.value

        assertEquals(value, ApiStatus.ERROR)
        eventsViewModel.setReturnError(false)
    }

}