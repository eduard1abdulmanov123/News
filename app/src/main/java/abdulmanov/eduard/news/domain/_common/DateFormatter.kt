package abdulmanov.eduard.news.domain._common

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private val basicDateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    
    fun convertDateToBasicFormat(dateStr:String, originalDateFormat:SimpleDateFormat): String{
        val date = if(dateStr.isEmpty()) Date() else originalDateFormat.parse(dateStr) ?: Date()
        return basicDateFormat.format(date)
    }

    fun parseDate(date: String): Date {
        return basicDateFormat.parse(date) ?: Date()
    }
}