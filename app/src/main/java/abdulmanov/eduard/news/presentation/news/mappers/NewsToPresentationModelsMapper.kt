package abdulmanov.eduard.news.presentation.news.mappers

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsToPresentationModelsMapper @Inject constructor(private val context: Context) {

    fun newsMapToPresentationModels(news: List<New>): List<NewPresentationModel> {
        return news.map {
            NewPresentationModel(
                id = it.id,
                title = it.title,
                link = it.link,
                description = it.description,
                date = mapDate(it.date),
                category = it.category,
                image = it.image,
                fullDescription = it.fullDescription,
                alreadyRead = it.alreadyRead
            )
        }
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