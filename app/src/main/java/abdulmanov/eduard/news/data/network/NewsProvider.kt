package abdulmanov.eduard.news.data.network

import abdulmanov.eduard.news.domain.models.New
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream
import java.lang.Exception

class NewsProvider(
    private val client: OkHttpClient,
    private val xmlParser: NewsXmlParser
) {

    fun getNews(): List<New> {
        val response = makeRequestToServer()
        return xmlParser.parseNewsXml(response)
    }

    private fun makeRequestToServer(): InputStream {
        val request = Request.Builder()
            .url(URL)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            response.body!!.byteStream()
        } else {
            throw Exception(SERVER_ERROR)
        }
    }

    companion object {
        const val SERVER_ERROR = "Server error"

        private const val URL = "https://www.vesti.ru/vesti.rss"
    }
}