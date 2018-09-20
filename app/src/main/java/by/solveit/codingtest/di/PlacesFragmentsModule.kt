package by.solveit.codingtest.di

import by.solveit.codingtest.ui.places.list.PlacesListFragment
import by.solveit.codingtest.ui.places.map.PlacesMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class PlacesFragmentsModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): PlacesListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): PlacesMapFragment
}
