package abdulmanov.eduard.news.presentation.livestream.changechanneldialog

import abdulmanov.eduard.news.domain.interactors.TvChannelsInteractor
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ChangeTvChannelViewModel @Inject constructor(
    private val tvChannelsInteractor: TvChannelsInteractor
) : ViewModel() {

    private val _tvChannels = MutableLiveData<List<TvChannel>>()
    val tvChannels: LiveData<List<TvChannel>>
        get() = _tvChannels

    init {
        _tvChannels.value = tvChannelsInteractor.getTvChannels().blockingGet()
    }

    fun selectTvChannel(tvChannel: TvChannel) {
        tvChannelsInteractor.saveIdSelectedTvChannel(tvChannel.id)
    }
}