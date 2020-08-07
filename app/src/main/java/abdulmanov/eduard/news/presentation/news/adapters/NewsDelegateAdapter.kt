package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.graphics.Typeface
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_new.*

class NewsDelegateAdapter(
    private val newItemClickListener: NewItemClickListener? = null
) : KDelegateAdapter<NewPresentationModel>() {

    override fun KViewHolder.onBind(item: NewPresentationModel) {

        itemNewRoot.transitionName = containerView.context.getString(R.string.root_transition, item.id)

        categoryTextView.text = item.category

        if (item.image.isNotEmpty()) {
            iconImageView.loadImg(item.image, R.color.placeholder)
        }

        titleTextView.text = item.title
        descriptionTextView.text = item.description
        dateTextView.text = item.date

        titleTextView.typeface =  if (item.alreadyRead) Typeface.DEFAULT else Typeface.DEFAULT_BOLD
        alreadyLabelImageView.visibility = if (item.alreadyRead) View.GONE else View.VISIBLE

        itemView.setOnClickListener { newItemClickListener?.onClick(item, this) }
    }

    override fun isForViewType(item: Any) = item is NewPresentationModel

    override fun getLayoutId() = R.layout.item_new

    override fun NewPresentationModel.getItemId(): Any = this.id

    override fun NewPresentationModel.getItemContent(): Any = this

    interface NewItemClickListener {
        fun onClick(new: NewPresentationModel, holder: RecyclerView.ViewHolder)
    }
}