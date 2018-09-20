package by.solveit.codingtest.ui.places.map

import android.os.Bundle
import by.solveit.codingtest.di.Injectable
import by.solveit.codingtest.di.ViewModelFactory
import by.solveit.codingtest.di.injectViewModel
import by.solveit.codingtest.ui.places.list.PlacesListViewModel
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

class PlacesMapFragment : SupportMapFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var placesListViewModel: PlacesListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placesListViewModel = injectViewModel(viewModelFactory)
    }


}