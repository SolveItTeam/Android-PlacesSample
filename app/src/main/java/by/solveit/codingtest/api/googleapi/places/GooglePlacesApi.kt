package by.solveit.codingtest.api.googleapi.places

import android.arch.lifecycle.LiveData
import by.solveit.codingtest.api.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GooglePlacesApi {

    /**
     * @param type type of
     * @see <a href="https://developers.google.com/mapPlaces/web-service/supported_types">mapPlaces</a>
     */
    @GET("nearbysearch/{output}")
    fun getNearby(
            @Path("output") output: String,
            @Query("key") key: String,
            @Query("location") location: String,
            @Query("radius") radius: Int,
            @Query("type") type: String? = null
    ): LiveData<ApiResponse<GoogleNearbyPlacesApiResponse>>


}