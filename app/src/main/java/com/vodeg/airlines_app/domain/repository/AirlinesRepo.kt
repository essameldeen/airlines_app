package com.vodeg.airlines_app.domain.repository

import com.vodeg.airlines_app.data.model.Airline

interface AirlinesRepo {

    suspend fun getAllAirLines(): MutableList<Airline>

    fun getCachedAirLines(): MutableList<Airline>

    suspend fun addNewAirLine(airline: Airline): Boolean
}