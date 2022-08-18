package com.murilobonetti.events.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murilobonetti.events.data.Event

class DetailViewModelFactory(private val event: Event) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST", "WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(event) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}