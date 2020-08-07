package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.news.adapters.FilterNewsDelegateAdapter
import abdulmanov.eduard.news.presentation.news.adapters.NewsDelegateAdapter
import abdulmanov.eduard.news.presentation.news.adapters.SeparateDelegateAdapter
import abdulmanov.eduard.news.presentation.news.dialogs.filter.FilterNewsBottomSheetDialog
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news),
    NewsDelegateAdapter.NewItemClickListener,
    FilterNewsDelegateAdapter.FilterItemClickListener,
    FilterNewsBottomSheetDialog.FilterNewsCallback {

    var currentSelectViewHolder: RecyclerView.ViewHolder? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackCommandClick()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initUI()

        viewModel.state.observe(viewLifecycleOwner, Observer(this::setState))
        viewModel.news.observe(viewLifecycleOwner, Observer((newsRecyclerView.adapter as CompositeDelegateAdapter)::swapData))
        viewModel.scrollToPositionEvent.observe(viewLifecycleOwner, Observer(newsRecyclerView::smoothScrollToPosition))
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer(errorTextView::setText))
        viewModel.messageEvent.observe(viewLifecycleOwner, Observer(this::showMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentSelectViewHolder = null
    }

    private fun initUI() {
        newsToolbar.run {
            setTitle(R.string.news_title)
            inflateMenu(R.menu.menu_news)
            setOnMenuItemClickListener(this@NewsFragment::onOptionsItemSelected)
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        newsRecyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CompositeDelegateAdapter(
                FilterNewsDelegateAdapter(this@NewsFragment),
                NewsDelegateAdapter(this@NewsFragment),
                SeparateDelegateAdapter()
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.openSettingItem -> viewModel.onOpenSettingScreenCommandClick()
        }
        return true
    }

    override fun onClick(new: NewPresentationModel, holder: RecyclerView.ViewHolder) {
        currentSelectViewHolder = holder
        viewModel.onOpenDetailsNewScreenCommandClick(new)
    }

    override fun onClick() {
        val dialog = FilterNewsBottomSheetDialog.newInstance()
        dialog.show(childFragmentManager, FilterNewsBottomSheetDialog.TAG)
    }

    override fun onChangeFilterNews() {
        viewModel.filterNews()
    }

    private fun setState(state: Int) {
        when (state) {
            NewsViewModel.VIEW_STATE_NEWS_EMPTY -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.GONE
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            NewsViewModel.VIEW_STATE_NEWS_PROGRESS -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.GONE
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            NewsViewModel.VIEW_STATE_NEWS_ERROR -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            NewsViewModel.VIEW_STATE_NEWS_REFRESH_AFTER_ERROR -> {
                swipeRefresh.visibility = View.VISIBLE
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            NewsViewModel.VIEW_STATE_NEWS_DATA -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            NewsViewModel.VIEW_STATE_NEWS_REFRESH_AFTER_DATA -> {
                swipeRefresh.visibility = View.VISIBLE
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showMessage(error: String) {
        Snackbar.make(swipeRefresh, error, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}