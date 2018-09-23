package by.solveit.codingtest.api.googleapi.places

import android.arch.lifecycle.LiveData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import by.solveit.codingtest.AppExecutors
import by.solveit.codingtest.api.ApiResponse
import by.solveit.codingtest.di.googleapi.GoogleApiKey
import by.solveit.codingtest.di.googleapi.GooglePlacesApiBaseUrl
import by.solveit.codingtest.di.googleapi.GooglePlacesApiFormat
import by.solveit.codingtest.di.googleapi.GooglePlacesApiOkHttpClient
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlacesService @Inject constructor(
        private val executors: AppExecutors,
        private val api: GooglePlacesApi,
        @GoogleApiKey private val apiKey: String,
        @GooglePlacesApiFormat private val format: String,
        @GooglePlacesApiOkHttpClient val okHttpClient: OkHttpClient,
        @GooglePlacesApiBaseUrl private val baseUrl: String
) {

    fun getNearby(lat: Double, lng: Double, radius: Int, type: String? = null) =
            api.getNearby(
                    output = format,
                    key = apiKey,
                    location = "$lat,$lng",
                    radius = radius,
                    type = type
            )

    fun getPhoto(reference: String, width: Int): LiveData<ApiResponse<Bitmap>> =
            object : LiveData<ApiResponse<Bitmap>>() {
                override fun onActive() {
                    executors.networkIO().execute {
                        try {
                            val response = okHttpClient.newCall(Request.Builder()
                                    .url(photoUrl(reference, width))
                                    .build())
                                    .execute()
                            val body = response.body()
                            if (body != null) {
                                val bitmap = BitmapFactory.decodeStream(body.byteStream())
                                postValue(ApiResponse.create(Response.success(bitmap)))
                            } else {
                                throw IOException("Empty body")
                            }
                        } catch (e: Exception) {
                            postValue(ApiResponse.create(e))
                        }
                    }
                }
            }

    private fun photoUrl(reference: String, width: Int) =
            HttpUrl.parse(baseUrl)?.newBuilder()!!
                    .addPathSegment("photo")
                    .addQueryParameter("maxwidth", width.toString())
                    .addQueryParameter("key", apiKey)
                    .addQueryParameter("photoreference", reference)
                    .build()
}