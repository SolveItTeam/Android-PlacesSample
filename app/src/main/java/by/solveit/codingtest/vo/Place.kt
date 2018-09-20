package by.solveit.codingtest.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "place")
data class Place(@PrimaryKey val id: String,
                 val name: String,
                 val icon: String,
                 @ColumnInfo(name = "photo_reference") val photoReference: String?,
                 val lat: Double,
                 val lng: Double) {

    constructor(googleNearbyPlace: GoogleNearbyPlace) : this(
            googleNearbyPlace.id,
            googleNearbyPlace.name,
            googleNearbyPlace.icon,
            googleNearbyPlace.photos?.let {
                if (it.isNotEmpty()) it[0].photoReference
                else null
            },
            googleNearbyPlace.geometry.location.lat,
            googleNearbyPlace.geometry.location.lng
    )

}