package com.vodeg.airlines_app.domain.repository

import com.vodeg.airlines_app.data.model.Airline

interface AirlinesRepo {
    fun getCachedAirLines(): MutableList<Airline>
    suspend fun getAllAirLines(): MutableList<Airline>
}