package abdulmanov.eduard.news.presentation.news.mappers

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain._common.DateFormatter
import abdulmanov.eduard.news.domain.models.news.New
import abdulmanov.eduard.news.presentation.news.models.FilterNewsPresentationModel
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import abdulmanov.eduard.news.presentation.news.models.SeparatePresentationModel
import android.content.Context
import java.util.*
import javax.inject.Inject

class NewsToPresentationModelsMapper @Inject constructor(private val context: Context) {

    fun newsMapToPresentationModels(news: List<New>, quantitySelectedCategories: Int): List<Any> {
        return mutableListOf<Any>().apply {
            val filterNewsPresentationModel = FilterNewsPresentationModel(quantitySelectedCategories)
            add(filterNewsPresentationModel)

            for (i in news.indices) {
                add(SeparatePresentationModel)
                add(news[i].toPresentationModel())
            }
        }
    }

    private fun New.toPresentationModel(): NewPresentationModel {
        return NewPresentationModel(
            id = id,
            title = title,
            link = link,
            description = description,
            date = date.preparedDate(),
            category = category,
            image = image,
            fullDescription = fullDescription,
            alreadyRead = alreadyRead
        )
    }

    private fun String.preparedDate(): String {
        val months = context.resources.getStringArray(R.array.months_genitive)
        val date = DateFormatter.parseDate(this)
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = months[calendar.get(Calendar.MONTH)]
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.getWithLeadingZeros(Calendar.HOUR_OF_DAY)
        val minute = calendar.getWithLeadingZeros(Calendar.MINUTE)

        return "$day $month $year, $hour:$minute"
    }

    private fun Calendar.getWithLeadingZeros(type: Int): String {
        val field = get(type)
        val fieldStr = field.toString()
        return if (fieldStr.length == 1) {
            "0$fieldStr"
        } else {
            fieldStr
        }
    }
}