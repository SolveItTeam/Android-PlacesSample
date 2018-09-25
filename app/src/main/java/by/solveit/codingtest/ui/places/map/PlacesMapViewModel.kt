package by.solveit.codingtest.ui.places.map

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.location.Location
import by.solveit.codingtest.repository.CurrentLocationRepository
import by.solveit.codingtest.repository.PlacesRepository
import by.solveit.codingtest.vo.Event
import by.solveit.codingtest.vo.Place
import by.solveit.codingtest.vo.Resource
import by.solveit.codingtest.vo.Status
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class PlacesMapViewModel @Inject constructor(
        application: Application,
        private val placesRepository: PlacesRepository,
        private val currentLocationRepository: CurrentLocationRepository
) : AndroidViewModel(application) {

    companion object {
        private const val IMAGE_MARKER_SIZE = 100
        private const val MARKERS_RADIUS = 2000
        private const val MARKERS_TYPE = "bar"
    }

    val imageMarkersToAdd = MediatorLiveData<List<ImageMarker>>()
    val imageMarkersToRemove = MutableLiveData<List<ImageMarker>>()
    val loading = MutableLiveData<Boolean>().apply { value = false }
    val error = MutableLiveData<Event<String>>()

    fun mapReady() {
        update()
    }

    fun update() {
        val currentLocation = currentLocationRepository.getCurrentLocation(LocationRequest.create())
        imageMarkersToAdd.addSource(currentLocation) { location: Location? ->
            location?.let {
                imageMarkersToAdd.removeSource(currentLocation)
                val places = placesRepository.getPlaces(
                        lat = it.latitude,
                        lng = it.longitude,
                        radius = MARKERS_RADIUS,
                        type = MARKERS_TYPE
                )
                imageMarkersToAdd.addSource(places) { resource: Resource<List<Place>>? ->
                    resource?.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                loading.value = false
                                updatePlaces(it.data?.map { it.toImageMarker() })
                                imageMarkersToAdd.removeSource(places)
                            }
                            Status.ERROR -> {
                                loading.value = false
                                imageMarkersToAdd.removeSource(places)
                                error.value = Event(it.message ?: "")
                            }
                            Status.LOADING -> {
                                loading.value = true
                                updatePlaces(it.data?.map { it.toImageMarker() })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updatePlaces(newMapPlaces: List<ImageMarker>?) {
        imageMarkersToRemove.value = imageMarkersToAdd.value
        imageMarkersToAdd.value = newMapPlaces
    }

    private fun Place.toImageMarker(): ImageMarker {
        val image = object : MediatorLiveData<Bitmap>() {
            init {
                this@toImageMarker.photoReference?.let {
                    val photoResource = placesRepository.getPhoto(it, IMAGE_MARKER_SIZE)
                    addSource(photoResource) { resource ->
                        resource?.let {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    this.removeSource(photoResource)
                                    value = resource.data
                                }
                                Status.ERROR -> {
                                    this.removeSource(photoResource)
                                    error.value = Event(it.message ?: "")
                                    value = null
                                }
                                Status.LOADING -> {
                                    value = null
                                }
                            }
                        }
                    }
                }
            }
        }
        val place = object : LiveData<LatLng>() {
            init {
                value = LatLng(this@toImageMarker.lat, this@toImageMarker.lng)
            }
        }
        return RoundedImageMarker(image, place, IMAGE_MARKER_SIZE)
    }
}