package by.solveit.codingtest.ui.places.map

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import timber.log.Timber
import javax.inject.Inject

class PlacesMapViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    init {
        Timber.w("init")
    }

}