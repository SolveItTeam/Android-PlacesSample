package by.solveit.codingtest.ui.places.list

import android.os.Bundle
import android.support.v4.app.Fragment
import by.solveit.codingtest.di.Injectable
import by.solveit.codingtest.di.ViewModelFactory
import by.solveit.codingtest.di.injectViewModel
import javax.inject.Inject

class PlacesListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var placesMapViewModel: PlacesListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placesMapViewModel = injectViewModel(viewModelFactory)
    }
}