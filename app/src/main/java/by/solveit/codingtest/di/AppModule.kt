package by.solveit.codingtest.di

import by.solveit.codingtest.AppExecutors
import by.solveit.codingtest.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @LoggingEnable
    fun provideLoggingEnable(): Boolean = BuildConfig.DEBUG

    @Provides
    fun provideAppExecutors() = AppExecutors()

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingEnable
