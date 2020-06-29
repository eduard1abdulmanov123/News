package abdulmanov.eduard.news.presentation.news

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news), BackButtonListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        openDetailsNews.setOnClickListener {
            viewModel.onOpenDetailsNewScreenCommandClick()
        }
    }

    private fun initUI(){
        newsToolbar.setTitle(R.string.news_title)
        newsToolbar.inflateMenu(R.menu.menu_news)
        newsToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.openLiveItem -> viewModel.onOpenLiveScreenCommandClick()
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackCommandClick()
        return true
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}