package com.vodeg.airlines_app.domain.usecase

import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo

class AddNewAirline constructor(private val airlinesRepo: AirlinesRepo) {

    suspend fun run(airline: Airline): Boolean {
        return airlinesRepo.addNewAirLine(airline)
    }
}