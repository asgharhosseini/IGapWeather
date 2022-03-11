package ir.ah.igapweather.ui.nextweather.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ir.ah.igapweather.base.BaseAdapter
import ir.ah.igapweather.data.model.DayWeather
import ir.ah.igapweather.databinding.ItemNextWeatherBinding
import ir.ah.igapweather.databinding.ItemNextWeatherForecastBinding
import ir.ah.igapweather.di.EndPoint.API_IMAGE_URL
import javax.inject.Inject

class NextWeatherAdapter @Inject constructor(private val glide: RequestManager) :
    BaseAdapter<DayWeather, NextWeatherAdapter.NextWeatherViewHolder>() {

    inner class NextWeatherViewHolder(private val binding: ItemNextWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

         fun bind(dayWeather: DayWeather, position: Int) {
            binding.apply {
                glide.load("${API_IMAGE_URL +dayWeather.weather.get(0).icon}.png").into(binding.itemCurrentForecastImageView)
                binding.itemRoot.setCardBackgroundColor(dayWeather.getColor())
                binding.itemCurrentForecastTemp.text="${dayWeather.main.temp}"+"°"
                binding.itemCurrentForecastTime.text=dayWeather.getHourOfDay()
                binding.itemCurrentForecastDay.text=dayWeather.getDay()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextWeatherViewHolder {
        val binding = ItemNextWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NextWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NextWeatherViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(currentItem)
            }
        }

    }


}