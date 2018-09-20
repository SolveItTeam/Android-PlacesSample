package by.solveit.codingtest.db

import android.arch.persistence.room.TypeConverter
import by.solveit.codingtest.util.fromJson
import com.google.gson.Gson

object CodingTestTypeConverters {

    private val gson: Gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToStringsList(data: String?): List<String>? {
        return data?.let {
            gson.fromJson(data)
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringsListToString(photos: List<String>?): String? {
        return photos?.let {
            gson.toJson(it)
        }
    }
}