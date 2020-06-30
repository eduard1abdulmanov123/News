package abdulmanov.eduard.news.presentation.news

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import abdulmanov.eduard.news.presentation.news.adapters.NewsDelegateAdapter
import abdulmanov.eduard.news.presentation.news.mappers.NewsToPresentationModelsMapper
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news), BackButtonListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsInteractor: NewsInteractor

    @Inject
    lateinit var mapper: NewsToPresentationModelsMapper

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

        viewModel.news.observe(viewLifecycleOwner, Observer(this::setData))
    }

    private fun initUI() {
        newsToolbar.setTitle(R.string.news_title)
        newsToolbar.inflateMenu(R.menu.menu_news)
        newsToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)

        newsRecyclerView.visibility = View.VISIBLE
        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerView.adapter = CompositeDelegateAdapter(
            NewsDelegateAdapter(object : NewsDelegateAdapter.NewItemClickListener {
                override fun onClick(new: NewPresentationModel) {
                    viewModel.onOpenDetailsNewScreenCommandClick(new)
                }
            })
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.openLiveItem -> viewModel.onOpenLiveScreenCommandClick()
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackCommandClick()
        return true
    }

    private fun setData(news: List<NewPresentationModel>) {
        (newsRecyclerView.adapter as CompositeDelegateAdapter).swapData(news)
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}