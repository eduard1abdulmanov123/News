package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.news.models.SeparatePresentationModel
import com.livermor.delegateadapter.delegate.KDelegateAdapter

class SeparateDelegateAdapter : KDelegateAdapter<SeparatePresentationModel>() {

    override fun KViewHolder.onBind(item: SeparatePresentationModel) {
    }

    override fun isForViewType(item: Any) = item is SeparatePresentationModel

    override fun getLayoutId() = R.layout.item_separate

    override fun SeparatePresentationModel.getItemId(): Any = this

    override fun SeparatePresentationModel.getItemContent(): Any = this
}