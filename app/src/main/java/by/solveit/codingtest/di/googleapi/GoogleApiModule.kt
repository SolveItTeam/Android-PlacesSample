package by.solveit.codingtest.di.googleapi

import by.solveit.codingtest.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class GoogleApiModule {

    @Provides
    @GoogleApiKey
    fun provideKey() = BuildConfig.GOOGLE_API_KEY

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GoogleApiKey