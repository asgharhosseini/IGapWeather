package ir.ah.igapweather.di

import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.*
import javax.inject.*

@TestInstallIn(components = [SingletonComponent::class], replaces = [DispatcherModule::class])
@Module
object DispatcherModuleTest {

    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}