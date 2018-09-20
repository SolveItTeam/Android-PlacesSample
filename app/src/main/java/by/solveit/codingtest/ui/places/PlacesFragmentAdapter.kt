package by.solveit.codingtest.ui.places

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import by.solveit.codingtest.ui.places.list.PlacesListFragment
import by.solveit.codingtest.ui.places.map.PlacesMapFragment

class PlacesFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            MAP -> PlacesMapFragment()
            LIST -> PlacesListFragment()
            else -> throw RuntimeException("implemented only two tabs")
        }
    }

    override fun getCount(): Int = 2

    companion object {
        const val MAP = 0
        const val LIST = 1
    }
}