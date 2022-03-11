package ir.ah.igapweather.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.*
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*

import javax.inject.*

/**
 * Used to provide classes with general uses
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
//    @Singleton
//    @Provides
//    fun provideForecastMapper(): ForecastMapper=ForecastMapper()
}