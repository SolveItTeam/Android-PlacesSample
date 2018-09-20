package by.solveit.codingtest.ui.places.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import timber.log.Timber
import javax.inject.Inject

class PlacesListViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    init {
        Timber.w("init")
    }

}