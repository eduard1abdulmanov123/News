package abdulmanov.eduard.news.data.network

import abdulmanov.eduard.news.domain.models.New
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

class NewsXmlParser {

    fun parseNewsXml(xml: InputStream): List<New> {
        try {
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
        } catch (e: Exception) {
            throw Exception(PARSE_ERROR)
        }
    }

    private fun getNew(itemElement: Element): New {
        return New(
            id = getId(itemElement),
            title = getNodeValue("title", itemElement),
            link = getNodeValue("link", itemElement),
            description = getNodeValue("description", itemElement),
            date = getNodeValue("pubDate", itemElement),
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