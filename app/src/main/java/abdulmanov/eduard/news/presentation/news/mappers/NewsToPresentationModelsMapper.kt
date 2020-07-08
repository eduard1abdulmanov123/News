package abdulmanov.eduard.news.presentation.news.mappers

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import abdulmanov.eduard.news.presentation.news.models.SeparatePresentationModel
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsToPresentationModelsMapper @Inject constructor(private val context: Context) {

    fun newsMapToPresentationModels(news: List<New>, page: Int): List<Any> {
        return mutableListOf<Any>().apply {
            for (i in news.indices) {
                if (i != 0 || page != 1)
                    add(SeparatePresentationModel)

                val new = mapNew(news[i])
                add(new)
            }
        }
    }

    private fun mapNew(new: New): NewPresentationModel {
        return NewPresentationModel(
            id = new.id,
            title = new.title,
            link = new.link,
            description = new.description,
            date = mapDate(new.date),
            category = new.category,
            image = new.image,
            fullDescription = new.fullDescription,
            alreadyRead = new.alreadyRead
        )
    }

    private fun mapDate(dateStr: String): String {
        val months = context.resources.getStringArray(R.array.months_genitive)
        val basicDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val date = basicDateFormat.parse(dateStr) ?: Date()
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = months[calendar.get(Calendar.MONTH)]
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = convertTime(calendar.get(Calendar.HOUR_OF_DAY))
        val minute = convertTime(calendar.get(Calendar.MINUTE))

        return "$day $month $year, $hour:$minute"
    }

    private fun convertTime(time: Int): String {
        val timeStr = time.toString()
        return if (timeStr.length == 1) {
            "0$timeStr"
        } else {
            timeStr
        }
    }
}