package abdulmanov.eduard.news.data.network

import abdulmanov.eduard.news.data._common.getDateAsString
import abdulmanov.eduard.news.data.db.models.NewDbModel
import android.util.Log
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class NewsXmlParser {

    fun parseNewsXml(xml: InputStream): List<NewDbModel> {
        try {
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
        } catch (e: Exception) {
            throw Exception(PARSE_ERROR)
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
            fullDescription = getNodeValue("yandex:full-text", itemElement)
        )
    }

    private fun getId(element: Element): Long {
        val link = getNodeValue("link", element)
        val id = link.substringAfterLast("id=")
        return id.toLong()
    }

    private fun getDate(element: Element): String {
        val dateStr= getNodeValue("pubDate", element)
        val basicDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US)
        val date = basicDateFormat.parse(dateStr) ?: Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.addMonthSinceLocalEqualUS()

        return calendar.getDateAsString()
    }

    private fun Calendar.addMonthSinceLocalEqualUS(){
        add(Calendar.MONTH,1)
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

    companion object {
        const val PARSE_ERROR = "Parse error"
    }
}