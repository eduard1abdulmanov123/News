package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_new.*

class NewsDelegateAdapter(
    private val newItemClickListener: NewItemClickListener? = null
) : KDelegateAdapter<NewPresentationModel>() {

    override fun KViewHolder.onBind(item: NewPresentationModel) {
        categoryTextView.text = item.category
        categoryTextView.transitionName = containerView.context.getString(R.string.category_transition, item.id)

        iconImageView.loadImg(item.image, R.color.placeholder)
        iconImageView.transitionName = containerView.context.getString(R.string.image_transition, item.id)

        titleTextView.text = item.title
        titleTextView.transitionName = containerView.context.getString(R.string.title_transition, item.id)

        descriptionTextView.text = item.description
        descriptionTextView.transitionName = containerView.context.getString(R.string.description_transition, item.id)

        dateTextView.text = item.date
        dateTextView.transitionName = containerView.context.getString(R.string.date_transition, item.id)

        val colorAlreadyRead = if(item.alreadyRead) R.color.already_read else 0
        clickItemView.setBackgroundResource(colorAlreadyRead)

        separator.visibility = if (adapterPosition == 0) View.INVISIBLE else View.VISIBLE

        clickItemView.setOnClickListener { newItemClickListener?.onClick(item, this) }
    }

    override fun isForViewType(item: Any) = item is NewPresentationModel

    override fun getLayoutId() = R.layout.item_new

    override fun NewPresentationModel.getItemId(): Any = this.id

    override fun NewPresentationModel.getItemContent(): Any = this

    interface NewItemClickListener {
        fun onClick(new: NewPresentationModel, holder: RecyclerView.ViewHolder)
    }
}