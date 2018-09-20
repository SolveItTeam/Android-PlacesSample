package by.solveit.codingtest.ui.places.map

import android.arch.lifecycle.Observer
import android.os.Bundle
import by.solveit.codingtest.di.Injectable
import by.solveit.codingtest.di.ViewModelFactory
import by.solveit.codingtest.di.injectViewModel
import by.solveit.codingtest.vo.Place
import com.google.android.gms.maps.SupportMapFragment
import timber.log.Timber
import javax.inject.Inject

class PlacesMapFragment : SupportMapFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var placesMapViewModel: PlacesMapViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placesMapViewModel = injectViewModel(viewModelFactory)
        placesMapViewModel.places.observe(this, Observer<List<Place>> {
            Timber.d(it?.toString())
        })
    }


}