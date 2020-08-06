package abdulmanov.eduard.news.domain.repositories

import abdulmanov.eduard.news.domain.models.feedback.FeedbackData

interface SettingRepository {

    fun getLinkSupplierWebsite(): String

    fun getFeedbackData(): FeedbackData

    fun setThemeType(type: Int)

    fun getThemeType(): Int
}