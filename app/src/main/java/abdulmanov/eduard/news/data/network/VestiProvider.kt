package abdulmanov.eduard.news.data.network

import abdulmanov.eduard.news.data._common.getDateAsString
import abdulmanov.eduard.news.data.db.models.NewDbModel
import abdulmanov.eduard.news.domain.models.Stream
import android.util.Log
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class VestiProvider(client: OkHttpClient) : NewsProvider(client) {

    override fun getUrlForNews(): String = "https://www.vesti.ru/vesti.rss"

    override fun getUrlForStream(): String = "https://player.vgtrk.com/iframe/datalive/id/21/sid/r24"

    override fun parseNewsXml(xml: InputStream): List<NewDbModel> {
        return mutableListOf<NewDbModel>().apply {
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val doc = documentBuilder.parse(xml)
            doc.documentElement.normalize()

            val nodeList = doc.getElementsByTagName("item")
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i)
                if (node.nodeType == Node.ELEMENT_NODE) {
                    val element = node as Element
                    val new = getNew(element)
                    add(new)
                }
            }
        }
    }

    private fun getNew(itemElement: Element): NewDbModel {
        return NewDbModel(
            id = getId(itemElement),
            title = getNodeValue("title", itemElement),
            link = getNodeValue("link", itemElement),
            description = getNodeValue("description", itemElement),
            date = getDate(itemElement),
            category = getNodeValue("category", itemElement),
            image = getAttribute("enclosure", "url", itemElement),
            fullDescription = getNodeValue("yandex:full-text", itemElement),
            alreadyRead = false
        )
    }

    private fun getId(element: Element): Long {
        val link = getNodeValue("link", element)
        val id = link.split("/").last()
        return id.toLong()
    }

    private fun getDate(element: Element): String {
        val dateStr = getNodeValue("pubDate", element)
        val basicDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US)
        val date = basicDateFormat.parse(dateStr) ?: Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.addMonthSinceLocalEqualUS()

        return calendar.getDateAsString()
    }

    private fun Calendar.addMonthSinceLocalEqualUS() {
        add(Calendar.MONTH, 1)
    }

    private fun getNodeValue(tag: String, element: Element): String {
        val node = element.getElementsByTagName(tag).item(0)
        if (node != null && node.hasChildNodes()) {
            if (node.firstChild != null && node.firstChild.nodeType == Node.TEXT_NODE) {
                return node.firstChild.nodeValue
            }
        }
        return ""
    }

    private fun getAttribute(tag: String, attributeName: String, element: Element): String {
        val node = element.getElementsByTagName(tag).item(0)
        if (node.hasAttributes()) {
            return (node as Element).getAttribute(attributeName)
        }
        return ""
    }

    override fun parseStreamJson(json: InputStream): Stream {
        val jsonString = json.convertToString()
        val jsonObject = JSONObject(jsonString)

        val media = jsonObject.getJSONObject("data")
            .getJSONObject("playlist")
            .getJSONArray("medialist")
            .getJSONObject(0)

        val title = media.get("title").toString()

        val picture = media.get("picture").toString()

        val streamUrl = media.getJSONObject("sources")
            .getJSONObject("m3u8")
            .get("auto")
            .toString() + ".m3u8"

        return Stream(title,picture,streamUrl)
    }

    private fun InputStream.convertToString():String{
        val bufferedReader = BufferedReader(InputStreamReader(this))
        val sb = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null){
            sb.append(line).append('\n')
            line = bufferedReader.readLine()
        }
        return sb.toString()
    }
}