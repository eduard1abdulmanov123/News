package abdulmanov.eduard.news.domain.repositories

import abdulmanov.eduard.news.domain.models.streams.TvChannel
import io.reactivex.Single

interface TvChannelsRepository {

    fun getTvChannels(): Single<List<TvChannel>>

    fun saveIdSelectedTvChannel(id: Long)

    fun getIdSelectedTvChannel(): Long
}