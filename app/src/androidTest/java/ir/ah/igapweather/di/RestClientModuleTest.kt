package ir.ah.igapweather.di
import ir.ah.igapweather.FakeNetworkConnectionInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.ah.igapweather.data.remote.ApiService

import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [RestClientModule::class])
@Module
object RestClientModuleTest {
    @Provides
    @Singleton
    internal fun provideMockApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)



    @Provides
    @Singleton
    internal fun provideMockHiCityRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        @Named("internetClient") fakeNetworkConnectionInterceptorClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:57594/")
        .client(okHttpClient)
        .client(fakeNetworkConnectionInterceptorClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    internal fun provideMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    @Named("internetClient")
    internal fun provideFakeNetworkConnectionInterceptorClient(fakeNetworkConnectionInterceptor: FakeNetworkConnectionInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(fakeNetworkConnectionInterceptor).build()

    @Provides
    @Singleton
    internal fun provideFakeNetworkConnectionInterceptor(): FakeNetworkConnectionInterceptor =
        FakeNetworkConnectionInterceptor()


}
