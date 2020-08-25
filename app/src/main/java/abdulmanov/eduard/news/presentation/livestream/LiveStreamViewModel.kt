package abdulmanov.eduard.news.presentation.livestream

import abdulmanov.eduard.news.domain.interactors.TvChannelsInteractor
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.presentation._common.base.BaseViewModel
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class LiveStreamViewModel @Inject constructor(
    @Named(CiceroneConstants.MAIN_ROUTER) private val router: Router,
    private val tvChannelsInteractor: TvChannelsInteractor
):BaseViewModel() {

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _selectedTvChannel = MutableLiveData<TvChannel>()
    val selectedTvChannel: LiveData<TvChannel>
        get() = _selectedTvChannel

    init {
        _state.value = VIEW_STATE_EMPTY
        refresh()
    }

    fun refresh(){
        _state.value = toggleState(ACTION_REFRESH)
    }

    fun prepareTvChannel(){
        _state.value = toggleState(ACTION_PREPARE_TV_CHANNEL)
    }

    fun errorWhilePreparingTvChannel(){
        _state.value = toggleState(ACTION_ERROR_WHILE_PREPARING_TV_CHANNEL)
    }

    fun tvChannelHaveBeenPrepared(){
        _state.value = toggleState(ACTION_TV_CHANNEL_HAVE_BEEN_PREPARED)
    }

    fun changeTvChannel(){
        _selectedTvChannel.value = tvChannelsInteractor.getSelectedTvChannel().blockingGet()
    }

    private fun toggleState(action: Int): Int{
        return when(action){
            ACTION_REFRESH -> {
                when(_state.value){
                    VIEW_STATE_EMPTY, VIEW_STATE_ERROR -> {
                        loadSelectedTvChannel()
                        VIEW_STATE_PROGRESS
                    }
                    else -> _state.value!!
                }
            }
            ACTION_LOAD_DATA -> {
                when(_state.value){
                    VIEW_STATE_PROGRESS -> {
                        VIEW_STATE_DATA
                    }
                    else -> _state.value!!
                }
            }
            ACTION_ERROR -> {
                when(_state.value){
                    VIEW_STATE_PROGRESS -> {
                        VIEW_STATE_ERROR
                    }
                    else -> _state.value!!
                }
            }
            ACTION_PREPARE_TV_CHANNEL -> {
                when(_state.value){
                    VIEW_STATE_DATA, VIEW_STATE_ERROR_WHILE_PREPARING_TV_CHANNEL,VIEW_STATE_TV_CHANNEL_HAVE_BEEN_PREPARED -> {
                        VIEW_STATE_PREPARE_TV_CHANNEL
                    }
                    else -> _state.value!!
                }
            }
            ACTION_ERROR_WHILE_PREPARING_TV_CHANNEL -> {
                when(_state.value){
                    VIEW_STATE_PREPARE_TV_CHANNEL -> {
                        VIEW_STATE_ERROR_WHILE_PREPARING_TV_CHANNEL
                    }
                    else -> _state.value!!
                }
            }
            ACTION_TV_CHANNEL_HAVE_BEEN_PREPARED -> {
                when(_state.value){
                    VIEW_STATE_PREPARE_TV_CHANNEL -> {
                        VIEW_STATE_TV_CHANNEL_HAVE_BEEN_PREPARED
                    }
                    else -> _state.value!!
                }
            }
            else -> _state.value!!
        }
    }

    private fun loadSelectedTvChannel(){
        tvChannelsInteractor.getSelectedTvChannel().safeSubscribe(
            {
               _state.value = toggleState(ACTION_LOAD_DATA)
                _selectedTvChannel.value = it
            },
            {
                _state.value = toggleState(ACTION_ERROR)
                _errorMessage.value = it.message
            }
        )
    }

    fun onBackCommandClick() = router.exit()

    companion object{
        const val ACTION_REFRESH = 1
        const val ACTION_LOAD_DATA = 2
        const val ACTION_ERROR = 3
        const val ACTION_PREPARE_TV_CHANNEL = 4
        const val ACTION_ERROR_WHILE_PREPARING_TV_CHANNEL = 5
        const val ACTION_TV_CHANNEL_HAVE_BEEN_PREPARED = 6

        const val VIEW_STATE_EMPTY = 1
        const val VIEW_STATE_PROGRESS = 2
        const val VIEW_STATE_ERROR = 3
        const val VIEW_STATE_DATA = 4
        const val VIEW_STATE_PREPARE_TV_CHANNEL = 5
        const val VIEW_STATE_ERROR_WHILE_PREPARING_TV_CHANNEL = 6
        const val VIEW_STATE_TV_CHANNEL_HAVE_BEEN_PREPARED = 7
    }
}