package abdulmanov.eduard.news.presentation.news.adapters

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.news.models.AdvertisingBannerPresentationModel
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.livermor.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.item_advertising_banner.view.*

class AdvertisingBannerDelegateAdapter: KDelegateAdapter<AdvertisingBannerPresentationModel>() {

    override fun createView(parent: ViewGroup): View {
        return super.createView(parent).apply {
            val adView = this.adView
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    override fun KDelegateAdapter.KViewHolder.onBind(item: AdvertisingBannerPresentationModel) {
    }

    override fun isForViewType(item: Any) = item is AdvertisingBannerPresentationModel

    override fun getLayoutId() = R.layout.item_advertising_banner

    override fun AdvertisingBannerPresentationModel.getItemId(): Any = this

    override fun AdvertisingBannerPresentationModel.getItemContent(): Any = this
}