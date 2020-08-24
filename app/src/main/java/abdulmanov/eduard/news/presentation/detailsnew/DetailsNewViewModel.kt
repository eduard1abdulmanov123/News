package abdulmanov.eduard.news.presentation.detailsnew

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation.navigation.NavigationConstants
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class DetailsNewViewModel @Inject constructor(
    @Named(NavigationConstants.NEWS_ROUTER) private val router: Router,
    private val newsInteractor: NewsInteractor
) : ViewModel() {

    private val _new = MutableLiveData<NewPresentationModel>()
    val new: LiveData<NewPresentationModel>
        get() = _new

    fun setNew(new: NewPresentationModel) {
        new.alreadyRead = true
        newsInteractor.markNewAsAlreadyRead(new.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        _new.value = new
    }

    fun onShareNewCommandClick() {
        _new.value ?: return

        router.navigateTo(Screens.SendData(_new.value!!.link))
    }

    fun onBackCommandClick() = router.exit()
}