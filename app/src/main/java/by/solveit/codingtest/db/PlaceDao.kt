package by.solveit.codingtest.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import by.solveit.codingtest.vo.Place

@Dao
abstract class PlaceDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(places: List<Place>)

    /**
     * 6371000 -  earth's mean radius, in meters
     * SQLite doesn't support any trigonometric functions by default
     */
    @Query("""
        SELECT  id,
                name,
                icon,
                photo_reference,
                lat,
                lng
        FROM place
        WHERE acos(sin(:requiredLat)*sin(radians(Lat)) + cos(:requiredLat)*cos(radians(Lat))*cos(radians(lng)-:requiredLng)) * 6371000 < :radius
    """)
    protected abstract fun getNearby(requiredLat: Double,
                                     requiredLng: Double,
                                     radius: Int): LiveData<List<Place>>

    @Query("SELECT * FROM place")
    abstract fun getAll(): LiveData<List<Place>>

}