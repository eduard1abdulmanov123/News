package abdulmanov.eduard.news.data.network

import abdulmanov.eduard.news.data.db.models.NewDbModel
import abdulmanov.eduard.news.domain.models.Stream
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream
import java.lang.Exception

abstract class NewsProvider(private val client: OkHttpClient) {

    fun getNews(): List<NewDbModel> {
        val url = getUrlForNews()
        val response = makeRequestToServer(url)
        return try {
            parseNewsXml(response)
        } catch (e: Exception) {
            Log.d("LogLog", e.message.toString())
            throw Exception(PARSE_ERROR)
        }
    }

    fun getStream(): Stream {
        val url = getUrlForStream()
        val response = makeRequestToServer(url)
        return try {
            parseStreamJson(response)
        } catch (e: Exception) {
            throw Exception(PARSE_ERROR)
        }
    }

    private fun makeRequestToServer(url: String): InputStream {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()
            return if (response.isSuccessful && response.body != null) {
                response.body!!.byteStream()
            } else {
                throw Exception(SERVER_ERROR)
            }
        } catch (e: Exception) {
            throw Exception(NETWORK_ERROR)
        }
    }

    abstract fun getUrlForNews(): String

    abstract fun getUrlForStream(): String

    abstract fun parseNewsXml(xml: InputStream): List<NewDbModel>

    abstract fun parseStreamJson(json: InputStream): Stream

    companion object {
        private const val SERVER_ERROR = "Ошибка сервера"
        private const val NETWORK_ERROR = "Неполадки с интернетом.\nПопробуйте воспользоваься другой сетью-мобильной или wi-fi."
        private const val PARSE_ERROR = "Ошибка при разборе XML"
    }
}