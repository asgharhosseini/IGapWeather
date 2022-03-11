package ir.ah.igapweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ah.igapweather.BuildConfig
import ir.ah.igapweather.data.remote.ApiService
import ir.ah.igapweather.data.repository.nextWeather.NextWeatherRepository
import ir.ah.igapweather.data.repository.nextWeather.NextWeatherRepositoryImpl
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepository
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepositoryImpl

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {



    @Provides
    @Singleton
    internal fun provideCurrentWeatherRepository(apiService: ApiService): CurrentWeatherRepository =
        if (BuildConfig.DEMO_MODE) CurrentWeatherRepositoryImpl(apiService) else CurrentWeatherRepositoryImpl(
            apiService
        )

    @Provides
    @Singleton
    internal fun provideNextWeatherRepository(apiService: ApiService): NextWeatherRepository =
        if (BuildConfig.DEMO_MODE) NextWeatherRepositoryImpl(apiService) else NextWeatherRepositoryImpl(
            apiService
        )


}