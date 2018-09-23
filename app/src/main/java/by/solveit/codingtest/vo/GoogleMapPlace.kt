package by.solveit.codingtest.vo

import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapPlace(place: Place) : MapPlace(place) {

    private var marker: Marker? = null

    override fun update(place: Place, icon: Bitmap?) {
        icon?.let {
            marker?.setIcon(BitmapDescriptorFactory.fromBitmap(it))
        }
        marker?.let {
            val newPosition = LatLng(place.lat, place.lng)
            if (it.position != newPosition) {
                it.position = newPosition
            }
        }
    }

    fun addOnMap(map: GoogleMap) {
        if (marker != null) throw IllegalStateException("Marker already added on map")

        marker = map.addMarker(MarkerOptions()
                .position(LatLng(place.lat, place.lng)))
        update(place, icon)
    }
}