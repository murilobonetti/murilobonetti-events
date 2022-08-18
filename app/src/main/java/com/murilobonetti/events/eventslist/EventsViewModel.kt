package com.murilobonetti.events.eventslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murilobonetti.events.data.Event
import com.murilobonetti.events.network.ApiStatus
import com.murilobonetti.events.network.EventApi
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    private val _navigateToEventDetail = MutableLiveData<Event?>()
    val navigateToEventDetails: LiveData<Event?> get() = _navigateToEventDetail

    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _events.value = EventApi.retrofitService.getEvents()
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