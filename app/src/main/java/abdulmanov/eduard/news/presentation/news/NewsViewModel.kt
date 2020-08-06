package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.models.news.New
import abdulmanov.eduard.news.presentation._common.base.BaseViewModel
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.mappers.NewsToPresentationModelsMapper
import abdulmanov.eduard.news.presentation.news.models.FilterNewsPresentationModel
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
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

    private val _scrollToPositionEvent = LiveEvent<Int>()
    val scrollToPositionEvent: LiveData<Int>
        get() = _scrollToPositionEvent

    private var cashedNews: List<New> = emptyList()

    init {
        _state.value = VIEW_STATE_NEWS_EMPTY
        getNews()
    }

    private fun getNews(){
        _state.value = VIEW_STATE_NEWS_PROGRESS
        newsInteractor.getNewsFilteredByCategory()
            .doOnSuccess { cashedNews = it }
            .map(mapper::newsMapToPresentationModels)
            .safeSubscribe(
                {
                    _state.value = VIEW_STATE_NEWS_DATA
                    val quantitySelectedCategories = newsInteractor.getQuantitySelectedCategories()
                    val filterNewsPresentationModel = FilterNewsPresentationModel(quantitySelectedCategories = quantitySelectedCategories)
                    _news.value = listOf(filterNewsPresentationModel).plus(it)
                },
                {
                    _state.value = VIEW_STATE_NEWS_ERROR
                    _errorMessage.value = it.message
                }
            )
    }

    fun refresh(){
        if(_state.value == VIEW_STATE_NEWS_DATA || _state.value == VIEW_STATE_NEWS_ERROR){
            when(_state.value){
                VIEW_STATE_NEWS_DATA -> _state.value = VIEW_STATE_NEWS_REFRESH_AFTER_DATA
                VIEW_STATE_NEWS_ERROR -> _state.value = VIEW_STATE_NEWS_REFRESH_AFTER_ERROR
            }

            newsInteractor.getNewsFilteredByCategory()
                .doOnSuccess { cashedNews = it }
                .map(mapper::newsMapToPresentationModels)
                .safeSubscribe(
                    {
                        _state.value = VIEW_STATE_NEWS_DATA
                        _scrollToPositionEvent.value = 0

                        val quantitySelectedCategories = newsInteractor.getQuantitySelectedCategories()
                        val filterNewsPresentationModel = FilterNewsPresentationModel(quantitySelectedCategories = quantitySelectedCategories)
                        _news.value = listOf(filterNewsPresentationModel).plus(it)
                    },
                    {
                        when(_state.value){
                            VIEW_STATE_NEWS_REFRESH_AFTER_ERROR -> {
                                _state.value = VIEW_STATE_NEWS_ERROR
                                _errorMessage.value = it.message
                            }
                            VIEW_STATE_NEWS_REFRESH_AFTER_DATA -> {
                                _state.value = VIEW_STATE_NEWS_DATA
                                _messageEvent.value = it.message
                            }
                        }
                    }
                )
        }else{
            _state.value = _state.value
        }
    }

    fun filterNews(){

        val news = newsInteractor.getCachedNewsFilteredByCategory()

        val quantitySelectedCategories = newsInteractor.getQuantitySelectedCategories()
        val filterNewsPresentationModel = FilterNewsPresentationModel(quantitySelectedCategories = quantitySelectedCategories)
        val newsPresentationModels = mapper.newsMapToPresentationModels(news)
        _news.value = listOf(filterNewsPresentationModel).plus(newsPresentationModels)
    }

    fun onOpenDetailsNewScreenCommandClick(new: NewPresentationModel) = router.navigateTo(Screens.DetailsNew(new))

    fun onOpenSettingScreenCommandClick() = router.navigateTo(Screens.Setting)

    fun onBackCommandClick() = router.exit()

    companion object{
        const val VIEW_STATE_NEWS_EMPTY = 1
        const val VIEW_STATE_NEWS_PROGRESS = 2
        const val VIEW_STATE_NEWS_ERROR = 3
        const val VIEW_STATE_NEWS_REFRESH_AFTER_ERROR = 4
        const val VIEW_STATE_NEWS_DATA = 5
        const val VIEW_STATE_NEWS_REFRESH_AFTER_DATA = 6
    }
}