package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.domain.repositories.TvChannelsRepository
import android.util.Log
import io.reactivex.Single

class TvChannelsInteractor(private val tvChannelsRepository: TvChannelsRepository) {

    fun getSelectedTvChannel(): Single<TvChannel>{
        return tvChannelsRepository.getTvChannels().map { tvChannels ->
            tvChannels.find { it.isSelected } ?: tvChannels.first()
        }
    }

    fun getTvChannels(): Single<List<TvChannel>>{
        return tvChannelsRepository.getTvChannels().map { tvChannels ->
            val isSelectedTvChannelMissing = tvChannels.none { it.isSelected }
            if(isSelectedTvChannelMissing){
                tvChannels.first().isSelected = true
            }
            tvChannels
        }
    }

    fun saveIdSelectedTvChannel(id:Long){
        tvChannelsRepository.saveIdSelectedTvChannel(id)
    }
}