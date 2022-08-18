package com.murilobonetti.events.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.murilobonetti.events.MainCoroutineRule
import com.murilobonetti.events.data.parameters.CheckinParameters
import com.murilobonetti.events.getOrAwaitValue
import com.murilobonetti.events.network.ApiStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckinViewModelTest {

    private lateinit var checkinViewModel: FakeCheckinViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        checkinViewModel = FakeCheckinViewModel()
    }

    @Test
    fun `Check-in successful status should return DONE`() {
        val checkinParameters =
            CheckinParameters(eventId = "1", name = "John Doe", "johndoe@test.com")
        checkinViewModel.doCheckin(checkinParameters)

        val status = checkinViewModel.status.getOrAwaitValue()
        assertEquals(status, ApiStatus.DONE)
    }

    @Test
    fun `Check-in failed status should return ERROR`() {
        checkinViewModel.setReturnError(true)
        val checkinParameters =
            CheckinParameters(eventId = "1", name = "John Doe", "johndoe@test.com")
        checkinViewModel.doCheckin(checkinParameters)

        val status = checkinViewModel.status.getOrAwaitValue()
        assertEquals(status, ApiStatus.ERROR)

        checkinViewModel.setReturnError(false)
    }

    @Test
    fun `Check-in successful dismissModal should be true`() {
        val checkinParameters =
            CheckinParameters(eventId = "1", name = "John Doe", "johndoe@test.com")
        checkinViewModel.doCheckin(checkinParameters)

        val value = checkinViewModel.dismissModal.getOrAwaitValue()
        assertEquals(value, true)
    }

    @Test
    fun `Check-in failed dismissModal should be false`() {
        checkinViewModel.setReturnError(true)
        val checkinParameters =
            CheckinParameters(eventId = "1", name = "John Doe", "johndoe@test.com")
        checkinViewModel.doCheckin(checkinParameters)

        val value = checkinViewModel.dismissModal.getOrAwaitValue()
        assertEquals(value, false)

        checkinViewModel.setReturnError(false)
    }
}