package ir.ah.igapweather.di
import com.bumptech.glide.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.components.*
import ir.ah.igapweather.ui.currentweather.adapter.CurrentForecastAdapter
import ir.ah.igapweather.ui.currentweather.adapter.NextWeatherForecastAdapter


@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {


    @Provides
    fun provideCurrentForecastAdapter(glide: RequestManager) = CurrentForecastAdapter(glide)


    @Provides
    fun provideNextWeatherForecastAdapter(glide: RequestManager) = NextWeatherForecastAdapter(glide)

}