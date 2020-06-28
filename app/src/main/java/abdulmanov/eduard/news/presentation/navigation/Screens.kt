package abdulmanov.eduard.news.presentation.navigation

import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.live.LiveFragment
import abdulmanov.eduard.news.presentation.news.NewsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object News : SupportAppScreen() {
        override fun getFragment() = NewsFragment.newInstance()
    }

    object Live : SupportAppScreen() {
        override fun getFragment() = LiveFragment.newInstance()
    }

    object DetailsNew : SupportAppScreen() {
        override fun getFragment() = DetailsNewFragment.newInstance()
    }
}