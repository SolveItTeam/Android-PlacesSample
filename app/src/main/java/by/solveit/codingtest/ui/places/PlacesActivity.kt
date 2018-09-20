package by.solveit.codingtest.ui.places

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem
import by.solveit.codingtest.R
import by.solveit.codingtest.di.ViewModelFactory
import by.solveit.codingtest.di.injectViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PlacesActivity : DaggerAppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        placesViewModel = injectViewModel(viewModelFactory)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = PlacesFragmentAdapter(supportFragmentManager)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_map -> viewPager.currentItem = PlacesFragmentAdapter.MAP
            R.id.button_list -> viewPager.currentItem = PlacesFragmentAdapter.LIST
            else -> return false
        }
        return true
    }
}
