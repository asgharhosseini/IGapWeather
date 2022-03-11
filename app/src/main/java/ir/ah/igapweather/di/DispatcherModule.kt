package ir.ah.igapweather.di

import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import kotlinx.coroutines.*
import javax.inject.*

/**
 * Used to provide the Coroutine Dispatcher class
 */
@InstallIn(SingletonComponent::class)
@Module
object DispatcherModule {

    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}