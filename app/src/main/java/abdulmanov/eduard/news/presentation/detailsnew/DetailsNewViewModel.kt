package abdulmanov.eduard.news.presentation.detailsnew

import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsNewViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    private val _new = MutableLiveData<NewPresentationModel>()
    val new: LiveData<NewPresentationModel>
        get() = _new

    private val _shareNewLiveEvent = LiveEvent<String>()
    val shareNewLiveEvent: LiveData<String>
        get() = _shareNewLiveEvent

    fun setNew(new: NewPresentationModel) {
        _new.value = new
    }

    fun shareNew() {
        if (new.value != null) {
            _shareNewLiveEvent.value = new.value!!.link
        }
    }

    fun onBackCommandClick() = router.exit()
}