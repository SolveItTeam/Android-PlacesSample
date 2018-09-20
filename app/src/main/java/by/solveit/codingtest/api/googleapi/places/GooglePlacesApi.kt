package by.solveit.codingtest.api.googleapi.places

import android.arch.lifecycle.LiveData
import by.solveit.codingtest.api.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GooglePlacesApi {

    @GET("nearbysearch/{output}")
    fun getNearby(@Path("output") output: String,
                  @Query("key") key: String,
                  @Query("location") location: String,
                  @Query("radius") radius: Int): LiveData<ApiResponse<GoogleNearbyPlacesApiResponse>>


}