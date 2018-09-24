package by.solveit.codingtest.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.graphics.Bitmap
import by.solveit.codingtest.AppExecutors
import by.solveit.codingtest.api.ApiEmptyResponse
import by.solveit.codingtest.api.ApiErrorResponse
import by.solveit.codingtest.api.ApiResponse
import by.solveit.codingtest.api.ApiSuccessResponse
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

    fun getPlaces(lat: Double, lng: Double, radius: Int, type: String? = null): LiveData<Resource<List<Place>>> =
            object : NetworkBoundResource<List<Place>, GoogleNearbyPlacesApiResponse>(appExecutors) {
                override fun saveCallResult(item: GoogleNearbyPlacesApiResponse) {
                    dao.insert(item.results.map { Place(it) })
                }

                override fun shouldFetch(data: List<Place>?) = true

                override fun loadFromDb(): LiveData<List<Place>> =
                        dao.getNearby(lat, lng, radius, limit = 20)

                override fun createCall(): LiveData<ApiResponse<GoogleNearbyPlacesApiResponse>> =
                        service.getNearby(lat, lng, radius, type)

            }.asLiveData()

    fun getPhoto(reference: String, width: Int): LiveData<Resource<Bitmap>> =
            object : MediatorLiveData<Resource<Bitmap>>() {
                init {
                    value = Resource.loading(null)
                    val apiResponse = service.getPhoto(reference, width)
                    addSource(apiResponse) { response: ApiResponse<Bitmap>? ->
                        response?.let {
                            removeSource(apiResponse)
                            value = when (it) {
                                is ApiEmptyResponse ->
                                    Resource.error("Empty response", null)
                                is ApiSuccessResponse ->
                                    Resource.success(it.body)
                                is ApiErrorResponse ->
                                    Resource.error(it.errorMessage, null)
                            }
                        }
                    }
                }

            }


}