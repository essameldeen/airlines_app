package com.vodeg.airlines_app.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.usecase.GetAllAirlines
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val usecase: GetAllAirlines) : ViewModel() {
    private val _allAirlines = MutableLiveData<MutableList<Airline>>()
    val allAirlines: LiveData<MutableList<Airline>>
        get() = _allAirlines

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String>
        get() = _showErrorMessage

    init {
        getAllAirlines()
    }

    private fun getAllAirlines() = viewModelScope.launch {
        _showProgress.postValue(true)
        try {

            _allAirlines.postValue(usecase.run())
            _showProgress.postValue(false)
        } catch (e: Exception) {
            _showProgress.postValue(false)
            _showErrorMessage.postValue(e.message)
        }
    }
}