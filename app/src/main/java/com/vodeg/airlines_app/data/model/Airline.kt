package com.vodeg.airlines_app.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName

@Entity(
    tableName = "airLine"
)
@Parcelize
data class Airline(
    @SerialName("country")
    val country: String?,
    @SerialName("established")
    val established: String?,
    @SerialName("head_quaters")
    val headQuaters: String?,
    @SerialName("id")
    val id: Long?,
    @SerialName("logo")
    val logo: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("slogan")
    val slogan: String?,
    @SerialName("website")
    val website: String?
): Parcelable