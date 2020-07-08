package abdulmanov.eduard.news.presentation.news

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.LinearInfiniteScrollListener
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.live.LiveActivity
import abdulmanov.eduard.news.presentation.news.adapters.LoadingDelegateAdapter
import abdulmanov.eduard.news.presentation.news.adapters.NewsDelegateAdapter
import abdulmanov.eduard.news.presentation.news.models.LoadingPresentationModel
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news), NewsDelegateAdapter.NewItemClickListener {

    var currentSelectViewHolder: RecyclerView.ViewHolder? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    private val adapter: CompositeDelegateAdapter
        get() = newsRecyclerView.adapter as CompositeDelegateAdapter

    private var scrollListener: LinearInfiniteScrollListener? = null

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
        newsContainerConstraintLayout.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        initUI()

        viewModel.state.observe(viewLifecycleOwner, Observer(this::setState))
        viewModel.messageLiveEvent.observe(viewLifecycleOwner, Observer(this::showMessage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollListener = null
        currentSelectViewHolder = null
    }

    private fun initUI() {
        newsToolbar.setTitle(R.string.news_title)
        newsToolbar.inflateMenu(R.menu.menu_news)
        newsToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        scrollListener = LinearInfiniteScrollListener(layoutManager, 0) {
            viewModel.loadNextPage()
        }
        newsRecyclerView.layoutManager = layoutManager
        newsRecyclerView.addOnScrollListener(scrollListener!!)
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerView.adapter = CompositeDelegateAdapter(
            NewsDelegateAdapter(this),
            LoadingDelegateAdapter()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.openLiveItem -> {
                val liveActivity = LiveActivity.newIntent(requireContext())
                startActivity(liveActivity)
            }
        }
        return true
    }

    override fun onClick(new: NewPresentationModel, holder: RecyclerView.ViewHolder) {
        currentSelectViewHolder = holder
        viewModel.onOpenDetailsNewScreenCommandClick(new)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setState(state: Paginator.State) {
        when (state) {
            is Paginator.State.Empty -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.GONE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            is Paginator.State.EmptyProgress -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.GONE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is Paginator.State.EmptyError -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorTextView.text = state.error.message
            }
            is Paginator.State.RefreshAfterEmptyError -> {
                swipeRefresh.isRefreshing = true
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                errorTextView.text = state.error.message
            }
            is Paginator.State.Data<*> -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Allow
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.swapData(state.data as List<Any>)
            }
            is Paginator.State.Refresh<*> -> {
                swipeRefresh.isRefreshing = true
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Allow
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.swapData(state.data as List<Any>)
            }
            is Paginator.State.NewPageProgress<*> -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.swapData((state.data + LoadingPresentationModel) as List<Any>)
            }
            is Paginator.State.FullData<*> -> {
                swipeRefresh.isRefreshing = false
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.swapData(state.data as List<Any>)
            }
            is Paginator.State.RefreshAfterFullData<*> -> {
                swipeRefresh.isRefreshing = true
                swipeRefresh.visibility = View.VISIBLE
                scrollListener?.state = LinearInfiniteScrollListener.PaginationState.Ban
                newsRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                progressBar.visibility = View.GONE
                adapter.swapData(state.data as List<Any>)
            }
        }
    }

    private fun showMessage(error: Throwable) {
        Snackbar.make(swipeRefresh, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}