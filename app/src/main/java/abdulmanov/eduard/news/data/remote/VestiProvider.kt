package abdulmanov.eduard.news.data.remote

import abdulmanov.eduard.news.domain._common.DateFormatter
import abdulmanov.eduard.news.domain.models.news.New
import okhttp3.OkHttpClient
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class VestiProvider(client: OkHttpClient) : NewsProvider(client) {

    override fun getUrlForNews(): String = "https://www.vesti.ru/vesti.rss"

    override fun parseNewsXml(xml: InputStream): List<New> {
        return mutableListOf<New>().apply {
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

    private fun getNew(itemElement: Element): New {
        return New(
            id = itemElement.getNodeValue("link").split("/").last().toLong(),
            title = itemElement.getNodeValue("title"),
            link = itemElement.getNodeValue("link"),
            description = itemElement.getNodeValue("description"),
            date = getDate(itemElement.getNodeValue("pubDate")),
            category = itemElement.getNodeValue("category"),
            image = itemElement.getAttribute("enclosure", "url"),
            fullDescription = getFullDescription(itemElement.getNodeValue("yandex:full-text"))
        )
    }

    private fun getDate(dateStr: String): String {
        val originalDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US)
        return DateFormatter.convertDateToBasicFormat(dateStr, originalDateFormat)
    }

    private fun getFullDescription(fullDescription: String): String{
        val paragraphs = fullDescription.split("\r\n").filter { it.isNotEmpty() && it != " "}

        val sb = StringBuilder()
        for(paragraph in paragraphs){
            sb.append(paragraph)
            sb.append("\n\n")
        }
        return sb.toString()
    }

    private fun Element.getNodeValue(tag: String): String {
        val node = getElementsByTagName(tag).item(0)
        if (node != null && node.hasChildNodes()) {
            if (node.firstChild != null && node.firstChild.nodeType == Node.TEXT_NODE) {
                return node.firstChild.nodeValue
            }
        }
        return ""
    }

    private fun Element.getAttribute(tag: String, attributeName: String): String {
        val node = getElementsByTagName(tag).item(0)
        if (node != null && node.hasAttributes()) {
            return (node as Element).getAttribute(attributeName)
        }
        return ""
    }
}