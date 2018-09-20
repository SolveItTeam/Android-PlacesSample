package by.solveit.codingtest.ui.places.map

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.location.Location
import by.solveit.codingtest.repository.CurrentLocationRepository
import by.solveit.codingtest.repository.PlacesRepository
import by.solveit.codingtest.vo.Place
import by.solveit.codingtest.vo.Resource
import by.solveit.codingtest.vo.Status
import com.google.android.gms.location.LocationRequest
import timber.log.Timber
import javax.inject.Inject

class PlacesMapViewModel
@Inject
constructor(application: Application,
            private val placesRepository: PlacesRepository,
            private val currentLocationRepository: CurrentLocationRepository) : AndroidViewModel(application) {

    private val currentLocation = currentLocationRepository.getCurrentLocation(LocationRequest.create())
    val places = MediatorLiveData<List<Place>>()

    init {
        places.addSource(currentLocation) { l: Location? ->
            l?.let {
                places.removeSource(currentLocation)
                val placesRequest = placesRepository.getPlaces(it.latitude, it.longitude, 2000)
                places.addSource(placesRequest) { resource: Resource<List<Place>>? ->
                    resource?.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                places.value = it.data
                                places.removeSource(placesRequest)
                            }
                            Status.ERROR -> {
                                Timber.d("error")
                                places.removeSource(placesRequest)
                            }
                            Status.LOADING -> {
                                Timber.d("loading")
                                places.value = it.data
                            }
                        }
                    }
                }
            }
        }
    }

}