package abdulmanov.eduard.news.data.remote

import abdulmanov.eduard.news.domain.models.streams.TvChannel
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class TvChannelsProvider(private val networkHelper: NetworkHelper) {

    fun getTvChannels(): List<TvChannel>{
        val tvChannels = getTvChannelsNetworkModel()
        return tvChannels.performActionsParallel {
            val liveId = getLiveIdForIdTvChannel(it.id)
            val liveStream = getLiveStreamForLiveId(liveId)
            TvChannel(it.id, it.name, liveStream.title, liveStream.url)
        }
    }

    private fun getTvChannelsNetworkModel(): List<TvChannelNetworkModel>{
        return mutableListOf<TvChannelNetworkModel>().apply {
            val url = "https://live.russia.tv/api/channels"
            val response = networkHelper.makeRequestToServer(url)
            val jsonObject = getJsonObjectFromInputStream(response)

            for(key in jsonObject.keys()){
                val channelJsonObject = jsonObject.getJSONObject(key)
                val tvChannelNetworkModel =
                    TvChannelNetworkModel(
                        id = channelJsonObject.getLong("id"),
                        name = channelJsonObject.getString("title")
                    )
                add(tvChannelNetworkModel)
            }
        }
    }

    private fun <T, V> List<T>.performActionsParallel(action:(T)->V):List<V>{
        val pool = Executors.newFixedThreadPool(this.size)
        val tasks = this.map {
            Callable { action.invoke(it) }
        }
        return try {
            pool.invokeAll(tasks).map { it.get() }
        }catch (e: InterruptedException){
            pool.shutdownNow()
            listOf()
        }
    }

    private fun getLiveIdForIdTvChannel(id:Long): Long {
        val url = "https://live.russia.tv/api/now/channel/$id"
        val response = networkHelper.makeRequestToServer(url)
        val jsonObject = getJsonObjectFromInputStream(response)

        return jsonObject.getLong("live_id")
    }

    private fun getLiveStreamForLiveId(liveId: Long): LiveStreamNetworkModel {
        val url = "https://player.vgtrk.com/iframe/datalive/id/$liveId"
        val response = networkHelper.makeRequestToServer(url)
        val jsonObject = getJsonObjectFromInputStream(response)

        val data = jsonObject.getJSONObject("data")
        val playlist = data.getJSONObject("playlist")
        val media = playlist.getJSONArray("medialist").getJSONObject(0)
        val title = media.getString("title")
        val urlLiveStream = media.getJSONObject("sources")
            .getJSONObject("m3u8")
            .getString("auto") + ".m3u8"

        return LiveStreamNetworkModel(
            title,
            urlLiveStream
        )
    }

    private fun getJsonObjectFromInputStream(inputStream: InputStream):JSONObject{
        val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        val jsonReader = JsonReader(inputStreamReader)
        val jsonElement = JsonParser.parseReader(jsonReader)
        val jsonString = jsonElement.asJsonObject.toString()
        return JSONObject(jsonString)
    }

    data class TvChannelNetworkModel(
        val id: Long,
        val name: String
    )

    data class LiveStreamNetworkModel(
        val title: String,
        val url: String
    )
}