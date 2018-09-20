package by.solveit.codingtest.di.googleapi

import by.solveit.codingtest.api.googleapi.places.GooglePlacesApi
import by.solveit.codingtest.di.LoggingEnable
import by.solveit.codingtest.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier


@Module(includes = [GoogleApiModule::class])
class GooglePlacesApiModule {

    @Provides
    @GooglePlacesApiFormat
    fun provideFormat() = "json"

    @Provides
    @GooglePlacesApiBaseUrl
    fun provideBaseUrl() = "https://maps.googleapis.com/maps/api/place/"

    @Provides
    @GooglePlacesApiOkHttpClient
    fun provideOkHttpClient(@LoggingEnable loggingEnable: Boolean): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (loggingEnable) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }

    @Provides
    @GooglePlacesApiRetrofit
    fun provideRetrofit(@GooglePlacesApiBaseUrl baseUrl: String,
                        @GooglePlacesApiOkHttpClient okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()


    @Provides
    fun provideGooglePlacesApi(@GooglePlacesApiRetrofit retrofit: Retrofit): GooglePlacesApi =
            retrofit.create(GooglePlacesApi::class.java)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GooglePlacesApiRetrofit

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GooglePlacesApiOkHttpClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GooglePlacesApiFormat

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GooglePlacesApiBaseUrl

