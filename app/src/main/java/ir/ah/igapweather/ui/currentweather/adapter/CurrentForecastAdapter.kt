package ir.ah.igapweather.ui.currentweather.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ir.ah.igapweather.base.BaseAdapter
import ir.ah.igapweather.data.model.DayWeather
import ir.ah.igapweather.databinding.ItemCurrentForecastBinding
import ir.ah.igapweather.di.EndPoint.API_IMAGE_URL
import javax.inject.Inject

class CurrentForecastAdapter @Inject constructor(private val glide: RequestManager) :
    BaseAdapter<DayWeather, CurrentForecastAdapter.CurrentForecastViewHolder>() {

    inner class CurrentForecastViewHolder(private val binding: ItemCurrentForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

         fun bind(dayWeather: DayWeather, position: Int) {
            binding.apply {
                glide.load("${API_IMAGE_URL +dayWeather.weather.get(0).icon}.png").into(binding.itemCurrentForecastImageView)

                binding.itemCurrentForecastTemp.text=dayWeather.main.temp.toString()+"°"
                binding.itemCurrentForecastTime.text=dayWeather.getHourOfDay()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentForecastViewHolder {
        val binding = ItemCurrentForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentForecastViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(currentItem)
            }
        }

    }


}