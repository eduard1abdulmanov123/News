package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import abdulmanov.eduard.news.presentation._common.extensions.visibility
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_new.*

class NewsDelegateAdapter(
    private val newsClickListener: NewsClickListener? = null
) : KDelegateAdapter<NewPresentationModel>() {

    override fun KViewHolder.onBind(item: NewPresentationModel) {
        categoryTextView.text = item.category
        nameTextView.text = item.title
        descriptionTextView.text = item.description
        dateTextView.text = item.date
        iconImageView.loadImg(item.image, R.color.colorPlaceholder)

        notAlreadyLabelImageView.visibility(!item.alreadyRead)
        nameTextView.typeface = if (item.alreadyRead) Typeface.DEFAULT else Typeface.DEFAULT_BOLD

        itemView.transitionName = containerView.context.getString(R.string.root_transition, item.id)
        itemView.setOnClickListener { newsClickListener?.onClickNew(item, this) }
    }

    override fun isForViewType(item: Any) = item is NewPresentationModel

    override fun getLayoutId() = R.layout.item_new

    override fun NewPresentationModel.getItemId(): Any = this.id

    override fun NewPresentationModel.getItemContent(): Any = this

    interface NewsClickListener {
        fun onClickNew(new: NewPresentationModel, holder: RecyclerView.ViewHolder)
    }
}