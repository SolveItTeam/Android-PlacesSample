package by.solveit.codingtest.ui.places.map

import android.arch.lifecycle.LiveData
import android.graphics.*
import com.google.android.gms.maps.model.LatLng
import kotlin.math.min

class RoundedImageMarker(
        image: LiveData<Bitmap>,
        position: LiveData<LatLng>,
        private val defaultSize: Int
) : ImageMarker(image, position) {

    companion object {
        private const val BORDER_SIZE = 10F
    }

    private val bitmapPaint = Paint().apply {
        isAntiAlias = true
    }
    private val circlePaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
    }


    override fun onImageUpdated(bitmap: Bitmap?) {
        var minSize = defaultSize.toFloat()
        if (bitmap != null) {
            minSize = min(bitmap.width, bitmap.height).toFloat()
            bitmapPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        } else {
            bitmapPaint.color = Color.WHITE
        }
        val borderRadius = minSize / 2 + BORDER_SIZE
        val bitmapRadius = minSize / 2
        val result = Bitmap.createBitmap(
                (borderRadius * 2).toInt(),
                (borderRadius * 2).toInt(),
                Bitmap.Config.ARGB_4444
        )
        val canvas = Canvas(result)
        canvas.drawCircle(
                borderRadius,
                borderRadius,
                borderRadius,
                circlePaint
        )
        val rect = RectF(
                BORDER_SIZE,
                BORDER_SIZE,
                bitmapRadius * 2 + BORDER_SIZE,
                bitmapRadius * 2 + BORDER_SIZE)
        canvas.drawRoundRect(rect, bitmapRadius, bitmapRadius, bitmapPaint)
        super.onImageUpdated(result)
    }
}