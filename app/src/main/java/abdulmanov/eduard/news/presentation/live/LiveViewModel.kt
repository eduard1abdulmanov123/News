package abdulmanov.eduard.news.presentation.live

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.models.Stream
import abdulmanov.eduard.news.presentation._common.base.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LiveViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel() {

    private val _stream = MutableLiveData<Stream>()
    val stream: LiveData<Stream>
        get() = _stream

    private val _stateView = MutableLiveData<Int>()
    val stateView: LiveData<Int>
        get() = _stateView

    fun loadUrl(){
        _stateView.value = STATE_VIEW_PROGRESS_BAR
        newsInteractor.getStream().safeSubscribe(
            {
                _stream.value = it
                _stateView.value = STATE_VIEW_SHOW_CONTENT
            },
            {
                _stateView.value = STATE_VIEW_SHOW_ERROR
            }
        )
    }

    companion object{
        const val STATE_VIEW_PROGRESS_BAR = 1
        const val STATE_VIEW_SHOW_CONTENT = 2
        const val STATE_VIEW_SHOW_ERROR = 3
    }
}