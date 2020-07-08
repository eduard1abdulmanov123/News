package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation._common.base.BaseViewModel
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.mappers.NewsToPresentationModelsMapper
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val router: Router,
    private val newsInteractor: NewsInteractor,
    private val mapper: NewsToPresentationModelsMapper
) : BaseViewModel() {

    private val _messageLiveEvent = LiveEvent<Throwable>()
    val messageLiveEvent: LiveData<Throwable>
        get() = _messageLiveEvent

    private val _state = MutableLiveData<Paginator.State>()
    val state: LiveData<Paginator.State>
        get() = _state

    private val paginator = Paginator.Store<NewPresentationModel>()

    private var pageDisposable: Disposable? = null

    init {
        paginator.render = { _state.postValue(it) }
        paginator.sideEffects.subscribe {
            when (it) {
                is Paginator.SideEffect.LoadPage -> loadNewPage(it.currentPage)
                is Paginator.SideEffect.ErrorEvent -> _messageLiveEvent.value = it.error
            }
        }.connect()

        refresh()
    }

    fun refresh() = paginator.proceed(Paginator.Action.Refresh)

    fun loadNextPage() = paginator.proceed(Paginator.Action.LoadMore)

    fun onOpenDetailsNewScreenCommandClick(new: NewPresentationModel) = router.navigateTo(Screens.DetailsNew(new))

    fun onBackCommandClick() = router.exit()

    private fun loadNewPage(page: Int) {
        pageDisposable?.dispose()
        pageDisposable = newsInteractor.getNews(page)
            .map(mapper::newsMapToPresentationModels)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    paginator.proceed(Paginator.Action.NewPage(page, it))
                },
                {
                    paginator.proceed(Paginator.Action.PageError(it))
                }
            )
        pageDisposable?.connect()
    }
}