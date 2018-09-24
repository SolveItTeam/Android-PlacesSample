package by.solveit.codingtest.ui.places.map

import android.arch.lifecycle.LiveData
import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

class RoundedImageMarker(
        image: LiveData<Bitmap>,
        position: LiveData<LatLng>
) : ImageMarker(image, position) {

    override fun onImageUpdated(bitmap: Bitmap?) {
        //TODO: make bitmap rounded
        super.onImageUpdated(bitmap)
    }
}