package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain.models.feedback.FeedbackData
import abdulmanov.eduard.news.domain.repositories.SettingRepository

class SettingInteractor(private val settingRepository: SettingRepository) {

    fun getLinkSupplierWebsite(): String {
        return settingRepository.getLinkSupplierWebsite()
    }

    fun getFeedbackData(): FeedbackData {
        return settingRepository.getFeedbackData()
    }

    fun setThemeType(type: Int) {
        settingRepository.setThemeType(type)
    }

    fun getThemeType(): Int {
        return settingRepository.getThemeType()
    }
}