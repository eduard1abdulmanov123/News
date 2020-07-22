package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain.repositories.SettingRepository

class SettingInteractor(private val settingRepository: SettingRepository) {

    fun getLinkSupplierWebsite() = settingRepository.getLinkSupplierWebsite()

    fun getFeedbackData() = settingRepository.getFeedbackData()

    fun setThemeType(type: Int) = settingRepository.setThemeType(type)

    fun getThemeType() = settingRepository.getThemeType()
}