package abdulmanov.eduard.news.presentation.livestream.changechanneldialog.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.presentation._common.extensions.inflate
import abdulmanov.eduard.news.presentation._common.extensions.visibility
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tv_channel.view.*

class TvChannelsAdapter(
    private val tvChannelsClickListener: TvChannelsClickListener? = null
): RecyclerView.Adapter<TvChannelsAdapter.ViewHolder>() {

    private val tvcChannels = mutableListOf<TvChannel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_tv_channel))
    }

    override fun getItemCount(): Int {
        return tvcChannels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(tvcChannels[position])
    }

    fun swapData(data: List<TvChannel>) {
        tvcChannels.clear()
        tvcChannels.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                tvChannelsClickListener?.onClickTvChannel(tvcChannels[adapterPosition])
            }
        }

        fun onBind(tvChannel: TvChannel) {
            itemView.run {
                nameTextView.text = tvChannel.name
                selectedImageView.isSelected = tvChannel.isSelected
                separator.visibility(adapterPosition != tvcChannels.size - 1, View.INVISIBLE)
            }
        }

    }

    interface TvChannelsClickListener{
        fun onClickTvChannel(tvChannel: TvChannel)
    }
}