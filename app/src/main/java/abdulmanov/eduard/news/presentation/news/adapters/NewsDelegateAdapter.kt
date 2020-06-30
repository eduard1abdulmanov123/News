package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.loadImg
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_new.*
import kotlinx.android.synthetic.main.item_new.view.*

class NewsDelegateAdapter(
    private val newItemClickListener: NewItemClickListener? = null
) : KDelegateAdapter<NewPresentationModel>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<Any>, position: Int) {
        super.onBindViewHolder(holder, items, position)
        holder.itemView.separator.visibility = if (position == items.size - 1) View.INVISIBLE else View.VISIBLE
    }

    override fun KViewHolder.onBind(item: NewPresentationModel) {
        clickItemView.setOnClickListener { newItemClickListener?.onClick(item) }
        categoryTextView.text = item.category
        iconImageView.loadImg(item.image, R.color.color_placeholder)
        titleTextView.text = item.title
        descriptionTextView.text = item.description
        dateTextView.text = item.date
    }

    override fun isForViewType(item: Any) = item is NewPresentationModel

    override fun getLayoutId() = R.layout.item_new

    override fun NewPresentationModel.getItemId(): Any = this.id

    override fun NewPresentationModel.getItemContent(): Any = this

    interface NewItemClickListener {
        fun onClick(new: NewPresentationModel)
    }
}