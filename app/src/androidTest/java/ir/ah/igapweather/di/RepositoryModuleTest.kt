package ir.ah.igapweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.ah.igapweather.BuildConfig
import ir.ah.igapweather.data.remote.ApiService
import ir.ah.igapweather.data.repository.NextWeather.NextWeatherRepository
import ir.ah.igapweather.data.repository.NextWeather.NextWeatherRepositoryImpl
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepository
import ir.ah.igapweather.data.repository.currentweather.CurrentWeatherRepositoryImpl

import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
@Module
object RepositoryModuleTest {



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