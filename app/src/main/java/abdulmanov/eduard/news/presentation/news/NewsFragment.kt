package abdulmanov.eduard.news.presentation.news

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R

class NewsFragment : Fragment(R.layout.fragment_news) {

    companion object {
        fun newInstance(): NewsFragment{
            return NewsFragment()
        }
    }
}