package com.murilobonetti.events.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murilobonetti.events.data.Event

class DetailViewModel(event: Event) : ViewModel() {

    private val _selectedEvent = MutableLiveData<Event>()

    val selectedEvent: LiveData<Event>
        get() = _selectedEvent

    init {
        _selectedEvent.value = event
    }
}