package by.solveit.codingtest.di

import by.solveit.codingtest.ui.places.PlacesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class PlacesActivityModule {
    @ContributesAndroidInjector(modules = [PlacesFragmentsModule::class])
    abstract fun contributeMainActivity(): PlacesActivity
}
