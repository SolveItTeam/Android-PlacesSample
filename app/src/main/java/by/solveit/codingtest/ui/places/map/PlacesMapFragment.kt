package by.solveit.codingtest.ui.places.map

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import by.solveit.codingtest.R
import by.solveit.codingtest.di.Injectable
import by.solveit.codingtest.di.ViewModelFactory
import by.solveit.codingtest.di.injectViewModel
import by.solveit.codingtest.util.isLocationPermissionGranted
import by.solveit.codingtest.vo.Event
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import timber.log.Timber
import javax.inject.Inject

class PlacesMapFragment : SupportMapFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: PlacesMapViewModel
    private var progressView: ProgressBar? = null
    private var map: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_places_map, viewGroup, false)
        val container = rootView.findViewById<FrameLayout>(R.id.container)
        val mapView = super.onCreateView(inflater, container, bundle)
        container.addView(mapView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressView = view.findViewById(R.id.progress_bar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        getMapAsync { map -> onMapReady(map) }
        viewModel.error.observe(this, Observer { event ->
            event?.let { onError(it) }
        })
        viewModel.loading.observe(this, Observer { loading ->
            loading?.let { onLoading(it) }
        })
        viewModel.imageMarkersToRemove.observe(this, Observer { markers ->
            markers?.let { onImageMarkersRemoved(it) }
        })
        viewModel.imageMarkersToAdd.observe(this, Observer { markers ->
            markers?.let { onImageMarkersAdded(it) }
        })
    }

    @SuppressLint("MissingPermission")
    private fun onMapReady(map: GoogleMap) {
        this.map = map
        map.setOnMyLocationButtonClickListener { onMyLocationButtonClick() }
        context?.let {
            if (isLocationPermissionGranted(it)) {
                map.isMyLocationEnabled = true
            }
        }
        viewModel.mapReady()
    }

    private fun onImageMarkersRemoved(imageMarkers: List<ImageMarker>) {
        Timber.d("onImageMarkersRemoved")
        map?.apply {
            imageMarkers.forEach {
                if (it.addedOnMap) {
                    it.removeFromMap()
                }
            }
        }

    }

    private fun onImageMarkersAdded(imageMarkers: List<ImageMarker>) {
        Timber.d("onImageMarkersAdded")
        map?.apply {
            imageMarkers.forEach {
                if (!it.addedOnMap) {
                    it.addOnMap(this)
                }
            }
        }
    }

    private fun onError(error: Event<String>) {
        error.getContentIfNotHandled()?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoading(loading: Boolean) {
        progressView?.let {
            it.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun onMyLocationButtonClick(): Boolean {
        viewModel.update()
        return false
    }
}