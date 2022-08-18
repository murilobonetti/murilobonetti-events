package com.murilobonetti.events.eventslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murilobonetti.events.data.Event
import com.murilobonetti.events.network.ApiStatus
import kotlinx.coroutines.launch

class FakeEventsViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    private val _navigateToEventDetail = MutableLiveData<Event?>()
    val navigateToEventDetails: LiveData<Event?> get() = _navigateToEventDetail

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun getAllEvents() {
        val eventList = listOf(
            Event("1", "teste1", 99.0, 0.0, 0.0, "imagem1", "desc1", 1534784400000),
            Event("2", "teste2", 99.0, 0.0, 0.0, "imagem2", "desc2", 1534784400000),
            Event("3", "teste3", 99.0, 0.0, 0.0, "imagem3", "desc3", 1534784400000),
            Event("4", "teste4", 99.0, 0.0, 0.0, "imagem4", "desc4", 1534784400000),
            Event("5", "teste5", 99.0, 0.0, 0.0, "imagem5", "desc5", 1534784400000)
        )

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                if (shouldReturnError) {
                    throw Exception("Test Exception")
                }
                _events.value = eventList
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _events.value = emptyList()
            }
        }
    }

    fun displayEventDetails(event: Event) {
        _navigateToEventDetail.value = event
    }

    fun displayEventDetailsComplete() {
        _navigateToEventDetail.value = null
    }
}