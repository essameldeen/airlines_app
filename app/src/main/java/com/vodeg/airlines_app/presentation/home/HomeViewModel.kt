package com.vodeg.airlines_app.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.usecase.AddNewAirline
import com.vodeg.airlines_app.domain.usecase.Filter
import com.vodeg.airlines_app.domain.usecase.GetAllAirlines
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val useCaseGetAllAirline: GetAllAirlines,
    private val useCaseAdd: AddNewAirline,
    private val useCaseFilter: Filter
) : ViewModel() {

    private val _allAirlines = MutableLiveData<MutableList<Airline>>()
    val allAirlines: LiveData<MutableList<Airline>>
        get() = _allAirlines

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String>
        get() = _showErrorMessage

    init {

        getAllAirlines()
    }

     fun getAllAirlines() = viewModelScope.launch {
        _showProgress.postValue(true)
        try {
            _allAirlines.postValue(useCaseGetAllAirline.run())
            _showProgress.postValue(false)
        } catch (e: Exception) {
            _showProgress.postValue(false)
            _showErrorMessage.postValue(e.message)
        }
    }

    fun addNewAirline(airline: Airline) = viewModelScope.launch {
        _showProgress.postValue(true)
        try {
            _addSuccess.postValue(useCaseAdd.run(airline))
            _showProgress.postValue(false)

        } catch (e: Exception) {
            _showErrorMessage.postValue(e.message)
        }

    }

    fun filter(query: String) = viewModelScope.launch {
        _showProgress.postValue(true)
        try {
            val filterList = useCaseFilter.run(query)
            if (filterList.isNullOrEmpty()) {
                _showErrorMessage.postValue("No Data find")
                _allAirlines.postValue(useCaseGetAllAirline.run())
            } else {
                _allAirlines.postValue(filterList)
            }
            _showProgress.postValue(false)
        } catch (e: Exception) {
            _showErrorMessage.postValue(e.message)
        }
    }
}