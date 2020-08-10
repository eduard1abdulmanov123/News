package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.extensions.visibility
import abdulmanov.eduard.news.presentation.news.models.FilterNewsPresentationModel
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_filter_news.*

class FilterNewsDelegateAdapter(
    private val filterNewsClickListener: FilterNewsClickListener? = null
): KDelegateAdapter<FilterNewsPresentationModel>() {

    override fun KViewHolder.onBind(item: FilterNewsPresentationModel) {
        quantitySelectedCategoryTextView.visibility(item.quantitySelectedCategories != 0)
        quantitySelectedCategoryTextView.text = item.quantitySelectedCategories.toString()

        itemView.setOnClickListener {
            filterNewsClickListener?.onClickFilterNews()
        }
    }

    override fun isForViewType(item: Any) = item is FilterNewsPresentationModel

    override fun getLayoutId() = R.layout.item_filter_news

    override fun FilterNewsPresentationModel.getItemId(): Any = 1

    override fun FilterNewsPresentationModel.getItemContent(): Any = this

    interface FilterNewsClickListener{
        fun onClickFilterNews()
    }
}