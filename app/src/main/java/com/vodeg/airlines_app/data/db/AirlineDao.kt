package com.vodeg.airlines_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vodeg.airlines_app.data.model.Airline

@Dao
interface AirlineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAirline(airline: Airline)

    @Insert
    suspend fun insertAll(airline: MutableList<Airline>)

    @Query("SELECT * FROM airLine")
    suspend fun getAllAirLines(): List<Airline>


}