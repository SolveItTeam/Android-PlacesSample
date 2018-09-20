package by.solveit.codingtest.di

import android.app.Application
import android.arch.persistence.room.Room
import by.solveit.codingtest.db.CodingTestDb
import by.solveit.codingtest.db.PlaceDao
import dagger.Module
import dagger.Provides

@Module
class CodingTestDatabaseModule {

    @Provides
    fun provideDatabase(app: Application) = Room
            .databaseBuilder(app, CodingTestDb::class.java, "coding_test.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun providePlacesDao(da: CodingTestDb) = da.placeDao()


}