package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.news.models.LoadingPresentationModel
import com.livermor.delegateadapter.delegate.KDelegateAdapter

class LoadingDelegateAdapter : KDelegateAdapter<LoadingPresentationModel>() {

    override fun KViewHolder.onBind(item: LoadingPresentationModel) {
    }

    override fun isForViewType(item: Any) = item is LoadingPresentationModel

    override fun getLayoutId() = R.layout.item_loading

    override fun LoadingPresentationModel.getItemId(): Any = this

    override fun LoadingPresentationModel.getItemContent(): Any = this
}