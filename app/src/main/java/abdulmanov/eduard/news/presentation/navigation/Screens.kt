package abdulmanov.eduard.news.presentation.navigation

import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.news.NewsFragment
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object News : SupportAppScreen() {
        override fun getFragment() = NewsFragment.newInstance()
    }

    data class DetailsNew(val new: NewPresentationModel) : SupportAppScreen() {
        override fun getFragment() = DetailsNewFragment.newInstance(new)
    }
}