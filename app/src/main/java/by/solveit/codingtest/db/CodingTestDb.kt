package by.solveit.codingtest.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import by.solveit.codingtest.vo.Place

@Database(
        entities = [
            Place::class
        ],
        version = 7,
        exportSchema = false
)
@TypeConverters(CodingTestTypeConverters::class)
abstract class CodingTestDb : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

}