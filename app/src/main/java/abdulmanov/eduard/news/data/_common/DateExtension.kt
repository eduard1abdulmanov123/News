package abdulmanov.eduard.news.data._common

import java.util.*

fun Calendar.getDateAsString(): String{
    val year = getWithLeadingZeros(Calendar.YEAR)
    val month = getWithLeadingZeros(Calendar.MONTH)
    val day = getWithLeadingZeros(Calendar.DAY_OF_MONTH)
    val hour = getWithLeadingZeros(Calendar.HOUR_OF_DAY)
    val minute = getWithLeadingZeros(Calendar.MINUTE)
    val second = getWithLeadingZeros(Calendar.SECOND)

    return "$year-$month-$day $hour:$minute:$second"
}

fun Calendar.getWithLeadingZeros(type:Int): String {
    val field = get(type)
    val fieldStr = field.toString()
    return if (fieldStr.length == 1) {
        "0$fieldStr"
    } else {
        fieldStr
    }
}