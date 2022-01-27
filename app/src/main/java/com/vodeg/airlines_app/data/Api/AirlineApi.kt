package com.vodeg.airlines_app.data.Api

import com.vodeg.airlines_app.data.Api.Responses.AirlinesResponse
import retrofit2.http.GET

interface AirlineApi {
    @GET("airlines")
    suspend fun getAllAirlines(): AirlinesResponse
}