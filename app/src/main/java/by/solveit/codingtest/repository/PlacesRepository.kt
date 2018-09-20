package by.solveit.codingtest.repository

import android.arch.lifecycle.LiveData
import by.solveit.codingtest.AppExecutors
import by.solveit.codingtest.api.ApiResponse
import by.solveit.codingtest.api.googleapi.places.GoogleNearbyPlacesApiResponse
import by.solveit.codingtest.api.googleapi.places.GooglePlacesService
import by.solveit.codingtest.db.PlaceDao
import by.solveit.codingtest.vo.Place
import by.solveit.codingtest.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesRepository @Inject constructor(private val service: GooglePlacesService,
                                           private val dao: PlaceDao,
                                           private val appExecutors: AppExecutors) {

    fun getPlaces(lat: Double, lng: Double, radius: Int): LiveData<Resource<List<Place>>> =
            object : NetworkBoundResource<List<Place>, GoogleNearbyPlacesApiResponse>(appExecutors) {
                override fun saveCallResult(item: GoogleNearbyPlacesApiResponse) {
                    dao.insert(item.results.map { Place(it) })
                }

                override fun shouldFetch(data: List<Place>?) = true

                //TODO: replace with getNearby
                override fun loadFromDb(): LiveData<List<Place>> =
                        dao.getAll()

                override fun createCall(): LiveData<ApiResponse<GoogleNearbyPlacesApiResponse>> =
                        service.getNearby(lat, lng, radius)

            }.asLiveData()

}