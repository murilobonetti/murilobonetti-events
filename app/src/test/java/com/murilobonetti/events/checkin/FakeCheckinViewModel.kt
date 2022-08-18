package com.murilobonetti.events.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murilobonetti.events.data.Result
import com.murilobonetti.events.data.parameters.CheckinParameters
import com.murilobonetti.events.network.ApiStatus
import kotlinx.coroutines.launch

class FakeCheckinViewModel : ViewModel() {

    private val _dismissModal = MutableLiveData<Boolean>()
    val dismissModal: LiveData<Boolean> get() = _dismissModal

    private val _status = MutableLiveData<ApiStatus?>()
    val status: LiveData<ApiStatus?> get() = _status

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun doCheckin(checkinParameters: CheckinParameters) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                if (shouldReturnError) {
                    throw Exception("Test Exception")
                }

                val result = Result(code = "200")

                if (result.code == "200") {
                    _dismissModal.value = true
                }
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _dismissModal.value = false
            }
        }
    }

    fun dismissModalComplete() {
        _dismissModal.value = false
    }

    fun handleResultComplete() {
        _status.value = null
    }
}