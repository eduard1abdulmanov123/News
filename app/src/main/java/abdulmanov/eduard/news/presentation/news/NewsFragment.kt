package abdulmanov.eduard.news.presentation.news

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import abdulmanov.eduard.news.presentation.news.adapters.NewsDelegateAdapter
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.fragment_news), BackButtonListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsInteractor: NewsInteractor

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

        newsInteractor.getNews()
            .subscribeOn(Schedulers.io())
            .map {
                it.map {
                    NewPresentationModel(
                        id = it.id,
                        title = it.title,
                        link = it.link,
                        description = it.description,
                        date = it.date,
                        category = it.category,
                        image = it.image,
                        fullDescription = it.fullDescription
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.forEach {
                        Log.d("NewsLog", "${it.id}  ${it.title}")
                    }
                    (newsRecyclerView.adapter as CompositeDelegateAdapter).swapData(it)
                },
                {

                }
            )

    }

    private fun initUI(){
        newsToolbar.setTitle(R.string.news_title)
        newsToolbar.inflateMenu(R.menu.menu_news)
        newsToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)

        newsRecyclerView.visibility = View.VISIBLE
        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerView.adapter = CompositeDelegateAdapter(NewsDelegateAdapter())
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