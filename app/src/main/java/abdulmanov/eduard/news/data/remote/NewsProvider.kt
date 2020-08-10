package abdulmanov.eduard.news.data.remote

import abdulmanov.eduard.news.domain.models.news.New
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream

abstract class NewsProvider(private val client: OkHttpClient) {

    fun getNews(): List<New> {
        val url = getUrlForNews()
        val response = makeRequestToServer(url)
        return try {
            parseNewsXml(response)
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
            return if (response.isSuccessful && response.body() != null) {
                response.body()!!.byteStream()
            } else {
                throw Exception(SERVER_ERROR)
            }
        } catch (e: Exception) {
            throw Exception(NETWORK_ERROR)
        }
    }

    abstract fun getUrlForNews(): String

    abstract fun parseNewsXml(xml: InputStream): List<New>

    companion object {
        private const val SERVER_ERROR = "Ошибка сервера"
        private const val NETWORK_ERROR = "Неполадки с интернетом.\nПопробуйте воспользоваться другой сетью-мобильной или wi-fi."
        private const val PARSE_ERROR = "Ошибка при разборе XML"
    }
}