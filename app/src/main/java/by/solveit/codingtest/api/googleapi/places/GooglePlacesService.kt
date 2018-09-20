package by.solveit.codingtest.api.googleapi.places

import by.solveit.codingtest.di.googleapi.GoogleApiKey
import by.solveit.codingtest.di.googleapi.GooglePlacesApiFormat
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlacesService @Inject constructor(private val api: GooglePlacesApi,
                                              @GoogleApiKey private val apiKey: String,
                                              @GooglePlacesApiFormat private val format: String) {

    fun getNearby(lat: Double, lng: Double, radius: Int) =
            api.getNearby(
                    format,
                    apiKey,
                    "$lat,$lng",
                    radius
            )

}