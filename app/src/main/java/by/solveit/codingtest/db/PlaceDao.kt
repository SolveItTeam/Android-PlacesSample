package by.solveit.codingtest.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import by.solveit.codingtest.vo.Place
import kotlin.math.cos
import kotlin.math.sin

@Dao
abstract class PlaceDao {

    companion object {
        private const val EARTH_RADIUS_M = 6371200.0
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(places: List<Place>)

    /**
     * @param radius in meters
     */
    fun getNearby(requiredLat: Double,
                  requiredLng: Double,
                  radius: Int) = getNearby(
            requiredSinLat = sin(Math.toRadians(requiredLat)),
            requiredCosLat = cos(Math.toRadians(requiredLat)),
            requiredSinLng = sin(Math.toRadians(requiredLng)),
            requiredCosLng = cos(Math.toRadians(requiredLng)),
            requiredDistanceFactor = cos(radius / EARTH_RADIUS_M)
    )

    /**
     * SQLite doesn't support any trigonometric functions by default
     */
    @Query("""
        SELECT  id,
                name,
                icon,
                photo_reference,
                lat,
                lng,
                sinLat,
                cosLat,
                sinLng,
                cosLng
        FROM place
        WHERE sinLat * :requiredSinLat + cosLat * :requiredCosLat * (sinLng * :requiredSinLng + cosLng * :requiredCosLng) > :requiredDistanceFactor
        ORDER BY sinLat * :requiredSinLat + cosLat * :requiredCosLat * (sinLng * :requiredSinLng + cosLng * :requiredCosLng) DESC
        LIMIT 20
    """)
    protected abstract fun getNearby(requiredSinLat: Double,
                                     requiredCosLat: Double,
                                     requiredSinLng: Double,
                                     requiredCosLng: Double,
                                     requiredDistanceFactor: Double): LiveData<List<Place>>

    @Query("SELECT * FROM place")
    abstract fun getAll(): LiveData<List<Place>>


}