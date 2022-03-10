package ir.ah.igapweather.base
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T,H : RecyclerView.ViewHolder?> : ListAdapter<T, H>(DiffCallback<T>()) {

    var onItemClickListener: ((T) -> Unit)? = null

    @JvmName("setOnItemClickListener1")
    fun setOnItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }
}