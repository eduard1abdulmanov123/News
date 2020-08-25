package abdulmanov.eduard.news.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream

class NetworkHelper(private val client: OkHttpClient) {

    fun makeRequestToServer(url: String): InputStream {
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

    companion object {
        private const val SERVER_ERROR = "Ошибка сервера"
        private const val NETWORK_ERROR = "Неполадки с интернетом.\nПопробуйте воспользоваться другой сетью-мобильной или wi-fi."
    }
}