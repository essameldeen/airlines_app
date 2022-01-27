package com.vodeg.airlines_app.domain.usecase

import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo

class GetAllAirlines constructor(private val airlinesRepo: AirlinesRepo) {

    suspend fun run(): MutableList<Airline> {
        val airlines = airlinesRepo.getCachedAirLines()
        if (airlines.isNullOrEmpty()) {
            return airlinesRepo.getAllAirLines()
        }
        return airlines
    }
}