package by.solveit.codingtest

import by.solveit.codingtest.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class CodingTestApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            AppInjector.init(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}