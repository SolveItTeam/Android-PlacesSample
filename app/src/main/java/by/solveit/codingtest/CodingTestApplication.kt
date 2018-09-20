package by.solveit.codingtest

import by.solveit.codingtest.api.googleapi.places.GooglePlacesService
import by.solveit.codingtest.di.AppInjector
import by.solveit.codingtest.di.LoggingEnable
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class CodingTestApplication : DaggerApplication() {

    @Inject
    lateinit var s: GooglePlacesService
    //TODO: inject it
    @Inject
    @LoggingEnable
    @JvmSynthetic
    var loggingEnable: Boolean = false

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            AppInjector.init(this)
}