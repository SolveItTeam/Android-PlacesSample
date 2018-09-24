package by.solveit.codingtest.ui.places.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Represent [Marker] on [GoogleMap]
 */
open class ImageMarker(
        private val image: LiveData<Bitmap>,
        private val position: LiveData<LatLng>
) {

    val addedOnMap: Boolean
        @Synchronized
        get() = marker != null
    protected var marker: Marker? = null
        private set
    private val imageObserver = Observer<Bitmap> { v -> onImageUpdated(v) }
    private val positionObserver = Observer<LatLng> { v -> onPositionUpdated(v) }

    @Synchronized
    fun addOnMap(map: GoogleMap) {
        if (marker != null) throw IllegalStateException("Marker already added")
        marker = map.addMarker(getMarkerOptions())
        image.observeForever(imageObserver)
        position.observeForever(positionObserver)
    }

    @Synchronized
    fun removeFromMap() {
        if (marker == null) throw IllegalStateException("Marker didn't added")
        marker?.let {
            it.remove()
            marker = null
            image.removeObserver(imageObserver)
            position.removeObserver(positionObserver)
        }
    }

    protected open fun getMarkerOptions() = MarkerOptions().apply {
        this@ImageMarker.position.value?.let {
            position(it)
        } ?: position(LatLng(0.0, 0.0))
        this@ImageMarker.image.value?.let {
            icon(BitmapDescriptorFactory.fromBitmap(it))
        }
    }

    protected open fun onImageUpdated(bitmap: Bitmap?) {
        marker?.let {
            it.setIcon(bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) })
        }
    }

    protected open fun onPositionUpdated(position: LatLng?) {
        marker?.let {
            it.position = position ?: LatLng(0.0, 0.0)
        }
    }
}