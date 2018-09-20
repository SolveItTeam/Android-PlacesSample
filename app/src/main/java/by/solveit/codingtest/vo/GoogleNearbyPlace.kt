package by.solveit.codingtest.vo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GoogleNearbyPlace(@Embedded val geometry: Geometry,
                             @PrimaryKey val id: String,
                             val icon: String,
                             val name: String,
                             val photos: List<Photo>?)

data class Geometry(val location: LatLng)

data class LatLng(val lat: Double,
                  val lng: Double)

data class Photo(@SerializedName("photo_reference") val photoReference: String)