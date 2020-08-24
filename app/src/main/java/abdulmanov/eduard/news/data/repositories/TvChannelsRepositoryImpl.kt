package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.local.sharedpreferences.TvChannelsSharedPreferences
import abdulmanov.eduard.news.data.remote.TvChannelsProvider
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.domain.repositories.TvChannelsRepository
import io.reactivex.Single

class TvChannelsRepositoryImpl(
    private val tvChannelsProvider: TvChannelsProvider,
    private val sharedPreferences: TvChannelsSharedPreferences
): TvChannelsRepository {

    private var cachedTvChannels: List<TvChannel>? = null

    override fun getTvChannels(): Single<List<TvChannel>> {
        return Single.create {
            cachedTvChannels = cachedTvChannels ?: tvChannelsProvider.getTvChannels()
            prepareTvChannels(cachedTvChannels!!)
            it.onSuccess(cachedTvChannels!!)
        }
    }

    private fun prepareTvChannels(tvChannels: List<TvChannel>){
        val idSelectedTvChannel = getIdSelectedTvChannel()

        tvChannels.forEach {
            it.isSelected = (it.id == idSelectedTvChannel)
        }
    }

    override fun saveIdSelectedTvChannel(id: Long) {
        sharedPreferences.setIdSelectedTvChannel(id)
    }

    override fun getIdSelectedTvChannel(): Long {
        return sharedPreferences.getIdSelectedTvChannel()
    }
}