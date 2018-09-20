package by.solveit.codingtest.repository

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import android.location.Location
import by.solveit.codingtest.util.isLocationPermissionGranted
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentLocationRepository @Inject constructor(private val app: Application) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(app)

    fun getCurrentLocation(locationRequest: LocationRequest) = object : LiveData<Location>() {

        private var locationCallbacks: LocationCallback? = null

        @SuppressLint("MissingPermission")
        override fun onActive() {
            super.onActive()
            if (!isLocationPermissionGranted(app)) return
            locationCallbacks = object : LocationCallback() {
                override fun onLocationResult(lr: LocationResult?) {
                    lr?.lastLocation?.let {
                        postValue(it)
                    }
                }
            }
            locationClient.requestLocationUpdates(locationRequest, locationCallbacks, null)
        }

        override fun onInactive() {
            super.onInactive()
            locationCallbacks?.let {
                locationClient.removeLocationUpdates(it)
            }
            locationCallbacks = null
        }

    }


}