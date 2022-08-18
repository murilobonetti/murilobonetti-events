package com.murilobonetti.events.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.murilobonetti.events.data.Event
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val event = Event("1", "teste1", 99.0, 0.0, 0.0, "imagem1", "desc1", 1534784400000)
        detailViewModel = DetailViewModel(event)
    }

    @Test
    fun `Select event is not null`() {
        assertNotNull(detailViewModel.selectedEvent.value)
    }
}