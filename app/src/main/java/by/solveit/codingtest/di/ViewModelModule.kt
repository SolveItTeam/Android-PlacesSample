package by.solveit.codingtest.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider

import by.solveit.codingtest.ui.places.PlacesViewModel;
import by.solveit.codingtest.ui.places.list.PlacesListViewModel;
import by.solveit.codingtest.ui.places.map.PlacesMapViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindPlacesViewModel(vm: PlacesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlacesListViewModel::class)
    abstract fun bindPlacesListViewModel(searchViewModel: PlacesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlacesMapViewModel::class)
    abstract fun bindPlacesMapViewModel(repoViewModel: PlacesMapViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
