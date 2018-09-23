package by.solveit.codingtest.ui.places.map

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.location.Location
import by.solveit.codingtest.api.ApiErrorResponse
import by.solveit.codingtest.api.ApiResponse
import by.solveit.codingtest.api.ApiSuccessResponse
import by.solveit.codingtest.repository.CurrentLocationRepository
import by.solveit.codingtest.repository.PlacesRepository
import by.solveit.codingtest.vo.*
import com.google.android.gms.location.LocationRequest
import javax.inject.Inject

class PlacesMapViewModel @Inject constructor(
        application: Application,
        private val placesRepository: PlacesRepository,
        private val currentLocationRepository: CurrentLocationRepository
) : AndroidViewModel(application) {

    val mapPlaces = MediatorLiveData<List<GoogleMapPlace>>()
    val loading = MutableLiveData<Boolean>().apply { value = false }
    val error = MutableLiveData<Event<String>>()

    fun mapReady() {
        update()
    }

    fun update() {
        val currentLocation = currentLocationRepository.getCurrentLocation(LocationRequest.create())
        mapPlaces.addSource(currentLocation) { location: Location? ->
            location?.let {
                mapPlaces.removeSource(currentLocation)
                val places = placesRepository.getPlaces(
                        lat = it.latitude,
                        lng = it.longitude,
                        radius = 2000,
                        type = "bar"
                )
                mapPlaces.addSource(places) { resource: Resource<List<Place>>? ->
                    resource?.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                loading.value = false
                                updatePlaces(it.data?.map { it.toGoogleMapPlace() })
                                mapPlaces.removeSource(places)
                            }
                            Status.ERROR -> {
                                loading.value = false
                                mapPlaces.removeSource(places)
                                error.value = Event(it.message ?: "")
                            }
                            Status.LOADING -> {
                                loading.value = true
                                updatePlaces(it.data?.map { it.toGoogleMapPlace() })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updatePlaces(mapPlaces: List<GoogleMapPlace>?) {
        this.mapPlaces.value?.let {
            it.forEach { it.isActive = false }
        }
        this.mapPlaces.value = mapPlaces
    }

    private fun Place.toGoogleMapPlace(): GoogleMapPlace {
        val mapPlace = GoogleMapPlace(this)
        photoReference?.let {
            val photo = placesRepository.getPhoto(it, 200)
            photo.observeForever(object : Observer<ApiResponse<Bitmap>> {
                override fun onChanged(response: ApiResponse<Bitmap>?) {
                    response?.let {
                        photo.removeObserver(this)
                        when (response) {
                            is ApiErrorResponse<Bitmap> ->
                                error.value = Event(response.errorMessage)
                            is ApiSuccessResponse<Bitmap> ->
                                mapPlace.icon = response.body
                        }
                    }
                }
            })
        }
        return mapPlace
    }
}