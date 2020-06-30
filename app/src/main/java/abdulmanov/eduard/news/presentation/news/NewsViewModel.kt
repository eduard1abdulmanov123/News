package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.mappers.NewsToPresentationModelsMapper
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val router: Router,
    private val newsInteractor: NewsInteractor,
    private val mapper: NewsToPresentationModelsMapper
) : ViewModel() {

    private val _news = MutableLiveData<List<NewPresentationModel>>()
    val news: LiveData<List<NewPresentationModel>>
        get() = _news

    init {
        getNews()
    }

    fun getNews() {
        newsInteractor.getNews()
            .subscribeOn(Schedulers.io())
            .map(mapper::newsMapToPresentationModels)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _news.value = it
                },
                {
                }
            )
    }

    fun onOpenLiveScreenCommandClick() = router.navigateTo(Screens.Live)

    fun onOpenDetailsNewScreenCommandClick(new: NewPresentationModel) = router.navigateTo(Screens.DetailsNew(new))

    fun onBackCommandClick() = router.exit()
}