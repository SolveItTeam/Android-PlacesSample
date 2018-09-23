package by.solveit.codingtest.vo

import android.graphics.Bitmap

abstract class MapPlace(protected val place: Place) {

    var icon: Bitmap? = null
        set(value) {
            if (isActive) {
                update(place, value)
            }
        }
    var isActive = true

    abstract fun update(place: Place, icon: Bitmap?)

}