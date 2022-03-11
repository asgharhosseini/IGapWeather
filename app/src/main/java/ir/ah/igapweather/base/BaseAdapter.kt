package ir.ah.igapweather.base
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @description
 * This class is designed to simplify the process of making adapter classes for recyclers
 */
abstract class BaseAdapter<T,H : RecyclerView.ViewHolder?> : ListAdapter<T, H>(DiffCallback<T>()) {

    var onItemClickListener: ((T) -> Unit)? = null

    @JvmName("setOnItemClickListener1")
    fun setOnItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }
}