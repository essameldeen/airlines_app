package com.vodeg.airlines_app.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName

@Entity(
    tableName = "airLine"
)
@Parcelize
data class Airline(
    @SerializedName("country")
    val country: String?,
    @SerializedName("established")
    val established: String?,
    @SerializedName("head_quaters")
    val headQuaters: String?,

    @PrimaryKey(autoGenerate = true)
    val idDb: Int = 0,

    @SerializedName("id")
    val id: Double? = 0.0,

    @SerializedName("logo")
    val logo: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("slogan")
    val slogan: String?,

    @SerializedName("website")
    val website: String?
) : Parcelable