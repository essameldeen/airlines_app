package com.vodeg.airlines_app.data.repository

import com.vodeg.airlines_app.data.Api.AirlineApi
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import retrofit2.Retrofit

class AirlineRepoImp constructor(retrofit: Retrofit) : AirlinesRepo {
    private val api = retrofit.create(AirlineApi::class.java)

    private var airlines = mutableListOf<Airline>()

    override fun getCachedAirLines(): MutableList<Airline> = airlines

    override suspend fun getAllAirLines(): MutableList<Airline> {
        val response = api.getAllAirlines()
        airlines = if (response.isNullOrEmpty()) {
            mutableListOf()
        } else {
            response
        }
        return airlines
    }
}