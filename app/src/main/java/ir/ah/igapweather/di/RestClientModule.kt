package ir.ah.igapweather.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ah.igapweather.data.remote.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Used to provide RestClient related classes
 */
@Module
@InstallIn(SingletonComponent::class)
object RestClientModule {

    @Provides
    @Singleton
    internal fun provideApiService(
        @WeatherRetrofit retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)





    @Provides
    @WeatherRetrofit
    @Singleton
    internal fun provideHiCityRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(EndPoint.STAGE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit