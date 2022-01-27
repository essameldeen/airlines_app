package com.vodeg.airlines_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vodeg.airlines_app.data.model.Airline

@Database(
    entities = [Airline::class],
    version = 1
)

abstract class AirlineDataBase : RoomDatabase() {
    abstract fun getAirlineDao(): AirlineDao
}