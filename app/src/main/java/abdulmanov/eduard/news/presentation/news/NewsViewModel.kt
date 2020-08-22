package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation._common.base.BaseViewModel
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.mappers.NewsToPresentationModelsMapper
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val router: Router,
    private val newsInteractor: NewsInteractor,
    private val mapper: NewsToPresentationModelsMapper
) : BaseViewModel() {

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    private val _news = MutableLiveData<List<Any>>()
    val news: LiveData<List<Any>>
        get() = _news

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _messageEvent = LiveEvent<String>()
    val messageEvent: LiveData<String>
        get() = _messageEvent

    init {
        _state.value = VIEW_STATE_NEWS_EMPTY
        refresh()
    }

    fun refresh() {
        _state.value = toggleState(ACTION_REFRESH)
    }

    @Suppress("UNCHECKED_CAST")
    private fun toggleState(action: Int, data: Any = Unit): Int {
        return when (action) {
            ACTION_REFRESH -> {
                when (_state.value) {
                    VIEW_STATE_NEWS_EMPTY -> {
                        loadNews()
                        VIEW_STATE_NEWS_PROGRESS
                    }
                    VIEW_STATE_NEWS_DATA -> {
                        loadNews()
                        VIEW_STATE_NEWS_REFRESH_AFTER_DATA
                    }
                    VIEW_STATE_NEWS_ERROR -> {
                        loadNews()
                        VIEW_STATE_NEWS_REFRESH_AFTER_ERROR
                    }
                    else -> _state.value!!
                }
            }
            ACTION_LOAD_DATA -> {
                when (_state.value) {
                    VIEW_STATE_NEWS_PROGRESS, VIEW_STATE_NEWS_REFRESH_AFTER_DATA, VIEW_STATE_NEWS_REFRESH_AFTER_ERROR -> {
                        _news.value = data as List<Any>
                        VIEW_STATE_NEWS_DATA
                    }
                    else -> _state.value!!
                }
            }
            ACTION_ERROR -> {
                when (_state.value) {
                    VIEW_STATE_NEWS_PROGRESS, VIEW_STATE_NEWS_REFRESH_AFTER_ERROR -> {
                        _errorMessage.value = (data as Throwable).message
                        VIEW_STATE_NEWS_ERROR
                    }
                    VIEW_STATE_NEWS_REFRESH_AFTER_DATA -> {
                        _messageEvent.value = (data as Throwable).message
                        VIEW_STATE_NEWS_DATA
                    }
                    else -> _state.value!!
                }
            }
            else -> _state.value!!
        }
    }

    private fun loadNews() {
        newsInteractor.getNewsFilteredByCategory()
            .map {
                val quantitySelectedCategories = newsInteractor.getQuantitySelectedCategories()
                mapper.newsMapToPresentationModels(it, quantitySelectedCategories)
            }
            .safeSubscribe(
                {
                    _state.value = toggleState(ACTION_LOAD_DATA, it)
                },
                {
                    _state.value = toggleState(ACTION_ERROR, it)
                }
            )
    }

    fun filterNews() {
        val news = newsInteractor.getCachedNewsFilteredByCategory()
        val quantitySelectedCategories = newsInteractor.getQuantitySelectedCategories()
        _news.value = mapper.newsMapToPresentationModels(news, quantitySelectedCategories)
    }

    fun onOpenDetailsNewScreenCommandClick(new: NewPresentationModel) = router.navigateTo(Screens.DetailsNew(new))

    fun onOpenLiveStreamNewScreenCommandClick(context: Context) = router.navigateTo(Screens.LiveStream(context))

    fun onOpenSettingScreenCommandClick() = router.navigateTo(Screens.Setting)

    fun onBackCommandClick() = router.exit()

    companion object {
        const val ACTION_REFRESH = 1
        const val ACTION_LOAD_DATA = 2
        const val ACTION_ERROR = 3

        const val VIEW_STATE_NEWS_EMPTY = 1
        const val VIEW_STATE_NEWS_PROGRESS = 2
        const val VIEW_STATE_NEWS_ERROR = 3
        const val VIEW_STATE_NEWS_REFRESH_AFTER_ERROR = 4
        const val VIEW_STATE_NEWS_DATA = 5
        const val VIEW_STATE_NEWS_REFRESH_AFTER_DATA = 6
    }
}