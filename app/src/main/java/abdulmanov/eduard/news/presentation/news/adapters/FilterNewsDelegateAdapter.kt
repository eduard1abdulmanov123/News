package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.news.models.FilterNewsPresentationModel
import android.view.View
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_filter_news.*

class FilterNewsDelegateAdapter(
    private val filterItemClickListener: FilterItemClickListener? = null
): KDelegateAdapter<FilterNewsPresentationModel>() {

    override fun KViewHolder.onBind(item: FilterNewsPresentationModel) {
        itemView.setOnClickListener {
            filterItemClickListener?.onClick()
        }

        quantitySelectedCategoryTextView.visibility = if(item.quantitySelectedCategories == 0) View.GONE else View.VISIBLE
        quantitySelectedCategoryTextView.text = item.quantitySelectedCategories.toString()
    }

    override fun isForViewType(item: Any) = item is FilterNewsPresentationModel

    override fun getLayoutId() = R.layout.item_filter_news

    override fun FilterNewsPresentationModel.getItemId(): Any = this.id

    override fun FilterNewsPresentationModel.getItemContent(): Any = this

    interface FilterItemClickListener{
        fun onClick()
    }
}