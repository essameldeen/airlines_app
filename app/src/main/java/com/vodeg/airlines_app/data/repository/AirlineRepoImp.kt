package com.vodeg.airlines_app.data.repository

import android.util.Log
import com.vodeg.airlines_app.data.Api.AirlineApi
import com.vodeg.airlines_app.data.db.AirlineDao
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import retrofit2.Retrofit

class AirlineRepoImp constructor(retrofit: Retrofit, private val airlineDao: AirlineDao) :
    AirlinesRepo {
    private val api = retrofit.create(AirlineApi::class.java)
    private var airlines = mutableListOf<Airline>()


    override suspend fun addNewAirLine(airline: Airline): Boolean {
        return try {
            airlineDao.addAirline(airline)
            airlines.add(airline)

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getCachedAirLines(): MutableList<Airline> = airlines

    override suspend fun getAllAirLines(): MutableList<Airline> {
        if (airlines.isNotEmpty()) {
            return airlines
        } else {
            // get from db
            val responseDb = getAirlinesFromDb()

            if (responseDb.isNullOrEmpty()) {

                // call server
                val responseApiCall = api.getAllAirlines()

                if (responseApiCall.isNullOrEmpty()) {
                    airlines = mutableListOf()
                } else {
                    airlines = responseApiCall
                    saveAllAirlinesToDb()
                }

            } else {
                airlines = responseDb as MutableList<Airline>
            }
        }

        return airlines
    }

    private suspend fun saveAllAirlinesToDb() {
        airlineDao.insertAll(airlines)
    }

    private suspend fun getAirlinesFromDb(): List<Airline> {
        return airlineDao.getAllAirLines()
    }
}