package com.vodeg.airlines_app.domain.usecase

import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo

class Filter constructor(private val airlinesRepo: AirlinesRepo) {

    fun run(query: String): MutableList<Airline> {
        val allData = airlinesRepo.getCachedAirLines()

        val filterList = allData.filter {
            it.name?.lowercase()?.contains(query) == true || it.country?.lowercase()
                ?.contains(query) == true
                    || it.id?.toString() == query
        }
        return filterList as MutableList<Airline>
    }
}