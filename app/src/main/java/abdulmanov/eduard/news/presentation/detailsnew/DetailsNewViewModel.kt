package abdulmanov.eduard.news.presentation.detailsnew

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsNewViewModel @Inject constructor(
    private val router: Router,
    private val newsInteractor: NewsInteractor
) : ViewModel() {

    private val _new = MutableLiveData<NewPresentationModel>()
    val new: LiveData<NewPresentationModel>
        get() = _new

    private val _shareNewLiveEvent = LiveEvent<String>()
    val sendNewLiveEvent: LiveData<String>
        get() = _shareNewLiveEvent

    fun setNew(new: NewPresentationModel) {
        new.alreadyRead = true
        newsInteractor.markNewAsAlreadyRead(new.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        _new.value = new
    }

    fun sendNew() {
        if (new.value != null) {
            _shareNewLiveEvent.value = new.value!!.link
        }
    }

    fun onBackCommandClick() = router.exit()
}